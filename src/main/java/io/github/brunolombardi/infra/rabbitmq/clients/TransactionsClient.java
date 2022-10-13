package io.github.brunolombardi.infra.rabbitmq.clients;

import io.github.brunolombardi.core.protocols.transactions.AccountTransactionCreatedEvent;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

import static io.github.brunolombardi.infra.rabbitmq.TransactionsChannelPoolListener.TRANSFERS_EXCHANGE_NAME;
import static io.github.brunolombardi.infra.rabbitmq.TransactionsChannelPoolListener.TRANSFERS_QUEUE_NAME;

@RabbitClient(TRANSFERS_EXCHANGE_NAME)
public interface TransactionsClient {
    @Binding(TRANSFERS_QUEUE_NAME)
    void publishTransactionCreatedEvent(AccountTransactionCreatedEvent accountTransactionCreatedEvent);
}
