package io.github.brunolombardi.core.usecases.transactions;

import io.github.brunolombardi.core.protocols.accounts.AccountNotFoundException;
import io.github.brunolombardi.core.protocols.accounts.AccountService;
import io.github.brunolombardi.core.protocols.transactions.*;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.UUID;

@Singleton
public class RestMakeTransactionUseCase implements MakeTransactionUseCase {

    @Inject
    private AccountService accountService;

    private AccountTransactionService accountTransactionService;

    @Override
    public TransactionResultDTO makeTransaction(MakeTransactionDTO makeTransactionDTO) throws InsufficientBalanceException {
        var originAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionDTO.getOriginAccount().getAccountBranch(),
                makeTransactionDTO.getOriginAccount().getAccountNumber()
        );
        var destinationAccountResult = accountService.findByAccountBranchAndAccountNumber(
                makeTransactionDTO.getDestinationAccount().getAccountBranch(),
                makeTransactionDTO.getDestinationAccount().getAccountNumber()
        );

        if (originAccountResult.isPresent() && destinationAccountResult.isPresent()) {
            var originAccount = originAccountResult.get();
            var destinationAccount = destinationAccountResult.get();
            var amount = makeTransactionDTO.getAmount();

            var isWithdrawSuccess = originAccount.withdraw(amount);
            if (isWithdrawSuccess) {
                destinationAccount.deposit(amount);
                AccountTransaction accountTransaction = AccountTransaction
                        .builder()
                        .transactionStatus(TransactionStatus.SUCCESS)
                        .originAccountId(originAccount.getId())
                        .destinationAccountId(destinationAccount.getId())
                        .occurredAt(LocalDateTime.now())
                        .transactionId(UUID.randomUUID().toString())
                        .amount(amount)
                        .build();
                accountTransactionService.save(accountTransaction);
                accountTransactionService.publishAccountTransactionCreatedEvent(new AccountTransactionCreatedEvent());
                return TransactionResultDTO
                        .builder()
                        .amount(amount)
                        .transactionStatus(accountTransaction.getTransactionStatus())
                        .transactionId(accountTransaction.getTransactionId())
                        .build();
            } else {
                throw new InsufficientBalanceException("Origin account balance is insufficient.");
            }
        }
        throw new AccountNotFoundException("Either origin or destination account is non existent");
    }
}
