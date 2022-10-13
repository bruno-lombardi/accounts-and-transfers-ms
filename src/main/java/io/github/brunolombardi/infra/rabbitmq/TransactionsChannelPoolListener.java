package io.github.brunolombardi.infra.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class TransactionsChannelPoolListener extends ChannelInitializer {

    public static final String TRANSFERS_QUEUE_NAME = "transfers";
    public static final String TRANSFERS_EXCHANGE_NAME = "microservices";

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare(TRANSFERS_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(TRANSFERS_QUEUE_NAME, true, false, false, null);
        channel.queueBind(TRANSFERS_QUEUE_NAME, TRANSFERS_EXCHANGE_NAME, TRANSFERS_QUEUE_NAME);
    }
}
