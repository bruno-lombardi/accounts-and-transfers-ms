package io.github.brunolombardi.core.usecases.transactions;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountNotFoundException;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.core.protocols.transactions.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Singleton
public class RestMakeTransactionUseCase implements MakeTransactionUseCase {

    @Inject
    private AccountService accountService;

    @Inject
    private AccountTransactionService accountTransactionService;

    @Override
    public Mono<TransactionResult> makeTransaction(MakeTransactionOptions makeTransactionOptions) throws InsufficientBalanceException, MakeTransactionErrorException, AccountNotFoundException {
        var originAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionOptions.getOriginAccount().getAccountBranch(),
                makeTransactionOptions.getOriginAccount().getAccountNumber()
        ).switchIfEmpty(Mono.error(new AccountNotFoundException("Either origin or destination account is non existent")));

        var destinationAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionOptions.getDestinationAccount().getAccountBranch(),
                makeTransactionOptions.getDestinationAccount().getAccountNumber()
        ).switchIfEmpty(Mono.error(new AccountNotFoundException("Either origin or destination account is non existent")));

        return Flux.combineLatest(originAccountResult, destinationAccountResult, (originAccount, destinationAccount) -> {
            if (originAccount.equals(destinationAccount))
                throw new MakeTransactionErrorException("Origin and destination account must be different");
            var amount = makeTransactionOptions.getAmount();
            var isWithdrawSuccess = originAccount.withdraw(amount);

            if (isWithdrawSuccess) {
                destinationAccount.deposit(amount);
                var trx = accountTransactionService.save(buildAccountTransaction(originAccount, destinationAccount, amount)).blockFirst();
                if (trx != null) {
                    return Flux.combineLatest(accountService.save(originAccount), accountService.save(destinationAccount), (origin, destination) -> {
                        var accountTransactionCreatedEvent = buildAccountTransactionCreatedEvent(
                                originAccount,
                                destinationAccount,
                                trx);
                        accountTransactionService.publishAccountTransactionCreatedEvent(accountTransactionCreatedEvent);
                        return buildTransactionResult(amount, trx);
                    }).next().block();
                }
                throw new MakeTransactionErrorException("Error persisting transaction");
            }
            throw new InsufficientBalanceException("Account has insufficient balance");
        }).next();
    }

    private TransactionResult buildTransactionResult(BigDecimal amount, AccountTransaction accountTransaction) {
        return TransactionResult.builder()
                                .amount(amount)
                                .transactionStatus(accountTransaction.getTransactionStatus())
                                .transactionId(accountTransaction.getTransactionId())
                                .build();
    }

    private AccountTransactionCreatedEvent buildAccountTransactionCreatedEvent(Account originAccount, Account destinationAccount, AccountTransaction accountTransaction) {
        return AccountTransactionCreatedEvent.builder()
            .transactionId(accountTransaction.getTransactionId())
            .amount(accountTransaction.getAmount())
            .destinationAccountId(destinationAccount.getId())
            .originAccountId(originAccount.getId())
            .build();
    }

    private AccountTransaction buildAccountTransaction(Account originAccount, Account destinationAccount, BigDecimal amount) {
        return AccountTransaction.builder()
            .transactionStatus(TransactionStatus.SUCCESS)
            .originAccountId(originAccount.getId())
            .destinationAccountId(destinationAccount.getId())
            .occurredAt(LocalDateTime.now())
            .transactionId(UUID.randomUUID().toString())
            .amount(amount)
            .build();
    }
}
