package io.github.brunolombardi.core.protocols.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountTransaction {
    private String transactionId;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private String originAccountId;
    private String destinationAccountId;
    private LocalDateTime occurredAt;
}
