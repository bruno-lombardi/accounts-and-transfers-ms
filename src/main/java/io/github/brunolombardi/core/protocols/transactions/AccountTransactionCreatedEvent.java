package io.github.brunolombardi.core.protocols.transactions;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class AccountTransactionCreatedEvent {
    private String originAccountId;
    private String destinationAccountId;
    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime occurredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTransactionCreatedEvent that = (AccountTransactionCreatedEvent) o;
        return Objects.equals(originAccountId, that.originAccountId) && Objects.equals(destinationAccountId,
                that.destinationAccountId) && Objects.equals(transactionId, that.transactionId) && Objects.equals(amount, that.amount) && Objects.equals(occurredAt, that.occurredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originAccountId, destinationAccountId, transactionId, amount, occurredAt);
    }
}
