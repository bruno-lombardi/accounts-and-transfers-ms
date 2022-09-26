package io.github.brunolombardi.core.data.transactions;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionCreatedEvent;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionService;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import jakarta.inject.Singleton;

@Singleton
public class DbAccountTransactionService implements AccountTransactionService {

    MongoAccountTransactionRepository mongoAccountTransactionRepository;

    @Override
    public AccountTransaction save(AccountTransaction accountTransaction) {
        var transaction = mongoAccountTransactionRepository
                .save(AccountTransactionEntity.fromAccountTransaction(accountTransaction));
        return transaction.toAccountTransaction();
    }

    @Override
    public void publishAccountTransactionCreatedEvent(AccountTransactionCreatedEvent transactionCreatedEvent) {

    }

}
