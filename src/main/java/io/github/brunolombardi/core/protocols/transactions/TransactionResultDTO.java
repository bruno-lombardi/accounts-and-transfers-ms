package io.github.brunolombardi.core.protocols.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResultDTO {
    String transactionId;
    TransactionStatus transactionStatus;
    BigDecimal amount;
}

