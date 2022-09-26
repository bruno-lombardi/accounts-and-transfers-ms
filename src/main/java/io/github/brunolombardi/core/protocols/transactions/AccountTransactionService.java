package io.github.brunolombardi.core.protocols.transactions;

public interface AccountTransactionService {
    AccountTransaction save(AccountTransaction accountTransaction);
    void publishAccountTransactionCreatedEvent(AccountTransactionCreatedEvent transactionCreatedEvent);
}
