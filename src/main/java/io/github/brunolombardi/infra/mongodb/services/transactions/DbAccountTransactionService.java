package io.github.brunolombardi.infra.mongodb.services.transactions;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionCreatedEvent;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionService;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import io.github.brunolombardi.infra.rabbitmq.clients.TransactionsClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class DbAccountTransactionService implements AccountTransactionService {

    @Inject
    MongoAccountTransactionRepository mongoAccountTransactionRepository;

    @Inject
    private TransactionsClient transactionsClient;

    private static final Logger LOG = LoggerFactory.getLogger(DbAccountTransactionService.class);

    @Override
    public Flux<AccountTransaction> save(AccountTransaction accountTransaction) {
        var transaction = (Flux<AccountTransactionEntity>) mongoAccountTransactionRepository
                .save(AccountTransactionEntity.fromAccountTransaction(accountTransaction));
        return transaction.map(AccountTransactionEntity::toAccountTransaction);
    }

    @Override
    public Mono<Void> publishAccountTransactionCreatedEvent(AccountTransactionCreatedEvent transactionCreatedEvent) {
        LOG.info("Publishing account transaction created event");
        transactionsClient.publishTransactionCreatedEvent(transactionCreatedEvent);
        return Mono.empty();
    }

}
