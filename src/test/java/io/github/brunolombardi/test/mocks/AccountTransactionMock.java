package io.github.brunolombardi.test.mocks;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.TransactionStatus;
import io.github.brunolombardi.infra.mongodb.entities.AccountTransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionMock {
    public static AccountTransaction getAccountTransaction() {
        return AccountTransaction
                .builder()
                .amount(BigDecimal.valueOf(100.0))
                .transactionId("1231231231231")
                .originAccountId("123456")
                .destinationAccountId("654321")
                .transactionStatus(TransactionStatus.SUCCESS)
                .occurredAt(LocalDateTime.now())
                .build();
    }

    public static AccountTransactionEntity getAccountTransactionEntity() {
        return AccountTransactionEntity
                .builder()
                .amount(BigDecimal.valueOf(100.0))
                .transactionId("1231231231231")
                .originAccountId("123456")
                .destinationAccountId("654321")
                .transactionStatus(TransactionStatus.SUCCESS)
                .occurredAt(LocalDateTime.now())
                .build();
    }
}
