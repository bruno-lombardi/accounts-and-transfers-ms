package io.github.brunolombardi.core.protocols.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionCreatedEvent {
    private String originAccountId;
    private String destinationAccountId;
    private String transactionId;
    private BigDecimal amount;
}
