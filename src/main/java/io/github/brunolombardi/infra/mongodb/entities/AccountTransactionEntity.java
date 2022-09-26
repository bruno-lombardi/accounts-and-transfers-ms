package io.github.brunolombardi.infra.mongodb.entities;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
import io.github.brunolombardi.core.protocols.transactions.TransactionStatus;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@MappedEntity
public class AccountTransactionEntity {
    @Id
    @GeneratedValue
    private String id;

    private String transactionId;

    private BigDecimal amount;

    private TransactionStatus transactionStatus;

    private String originAccountId;

    private String destinationAccountId;

    private LocalDateTime occurredAt;

    public AccountTransaction toAccountTransaction() {
        return AccountTransaction
                .builder()
                .amount(getAmount())
                .transactionId(getTransactionId())
                .occurredAt(getOccurredAt())
                .destinationAccountId(getDestinationAccountId())
                .originAccountId(getOriginAccountId())
                .transactionStatus(getTransactionStatus())
                .build();
    }

    public static AccountTransactionEntity fromAccountTransaction(AccountTransaction accountTransaction) {
        return AccountTransactionEntity
                .builder()
                .transactionId(accountTransaction.getTransactionId())
                .originAccountId(accountTransaction.getOriginAccountId())
                .destinationAccountId(accountTransaction.getDestinationAccountId())
                .amount(accountTransaction.getAmount())
                .occurredAt(accountTransaction.getOccurredAt())
                .transactionStatus(accountTransaction.getTransactionStatus())
                .build();
    }
}
