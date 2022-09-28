package io.github.brunolombardi.core.usecases.transactions;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.core.protocols.accounts.AccountNotFoundException;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.core.protocols.transactions.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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
    public TransactionResult makeTransaction(MakeTransactionOptions makeTransactionOptions) throws InsufficientBalanceException, AccountNotFoundException {
        var originAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionOptions.getOriginAccount().getAccountBranch(),
                makeTransactionOptions.getOriginAccount().getAccountNumber()
        );
        var destinationAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionOptions.getDestinationAccount().getAccountBranch(),
                makeTransactionOptions.getDestinationAccount().getAccountNumber()
        );

        if (originAccountResult.isPresent() && destinationAccountResult.isPresent()) {
            var originAccount = originAccountResult.get();
            var destinationAccount = destinationAccountResult.get();
            var amount = makeTransactionOptions.getAmount();

            var isWithdrawSuccess = originAccount.withdraw(amount);
            if (isWithdrawSuccess) {
                destinationAccount.deposit(amount);
                var accountTransaction = accountTransactionService.save(buildAccountTransaction(originAccount, destinationAccount, amount));
                var accountTransactionCreatedEvent = buildAccountTransactionCreatedEvent(
                        originAccount,
                        destinationAccount,
                        accountTransaction);
                accountTransactionService.publishAccountTransactionCreatedEvent(accountTransactionCreatedEvent);
                return buildTransactionResult(amount, accountTransaction);
            }
            throw new InsufficientBalanceException("Origin account balance is insufficient.");
        }
        throw new AccountNotFoundException("Either origin or destination account is non existent");
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
