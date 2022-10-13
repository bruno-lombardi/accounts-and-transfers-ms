package io.github.brunolombardi.infra.mongodb.services.transactions;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.AccountTransactionCreatedEvent;
import io.github.brunolombardi.core.protocols.transactions.TransactionStatus;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;
import io.github.brunolombardi.infra.mongodb.repositories.MongoAccountTransactionRepository;
import io.github.brunolombardi.infra.mongodb.services.transactions.DbAccountTransactionService;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MicronautTest(transactional = false)
class DbAccountTransactionServiceTest {

    @Inject
    private DbAccountTransactionService accountTransactionService;

    @Inject
    private MongoAccountTransactionRepository mongoAccountTransactionRepository;

    @MockBean(MongoAccountTransactionRepository.class)
    private MongoAccountTransactionRepository mongoAccountTransactionRepositoryMock() {
        return mock(MongoAccountTransactionRepository.class);
    }

    @Test
    void shouldSaveAccountTransaction() {
        when(mongoAccountTransactionRepository.save(any(AccountTransactionEntity.class)))
                .thenReturn(Flux.just(mock(AccountTransactionEntity.class)));
        var accountTransaction = AccountTransaction
                .builder()
                .destinationAccountId("123")
                .originAccountId("123")
                .transactionId(UUID.randomUUID().toString())
                .transactionStatus(TransactionStatus.SUCCESS)
                .amount(BigDecimal.valueOf(100.0))
                .occurredAt(LocalDateTime.now())
                .build();
        accountTransactionService.save(accountTransaction);
        verify(mongoAccountTransactionRepository, times(1))
                .save(any(AccountTransactionEntity.class));
    }

    @Test
    void shouldPublishAccountTransactionCreatedEvent() {
        var accountTransactionCreatedEvent = AccountTransactionCreatedEvent.builder().build();
        Assertions.assertDoesNotThrow(() -> {
            accountTransactionService.publishAccountTransactionCreatedEvent(accountTransactionCreatedEvent);
        });
    }

}
