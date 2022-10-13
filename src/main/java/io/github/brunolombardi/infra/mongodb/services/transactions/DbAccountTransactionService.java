package io.github.brunolombardi.infra.mongodb.services.transactions;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionCreatedEvent;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionService;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class DbAccountTransactionService implements AccountTransactionService {

    @Inject
    MongoAccountTransactionRepository mongoAccountTransactionRepository;

    @Override
    public Flux<AccountTransaction> save(AccountTransaction accountTransaction) {
        var transaction = (Flux<AccountTransactionEntity>) mongoAccountTransactionRepository
                .save(AccountTransactionEntity.fromAccountTransaction(accountTransaction));
        return transaction.map(AccountTransactionEntity::toAccountTransaction);
    }

    @Override
    public Mono<Void> publishAccountTransactionCreatedEvent(AccountTransactionCreatedEvent transactionCreatedEvent) {
        return null;
    }

}
