package io.github.brunolombardi.core.protocols.transactions;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeTransactionDTO {
    private AccountParams originAccount;
    private AccountParams destinationAccount;
    private BigDecimal amount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountParams {
        private String accountBranch;
        private String accountNumber;
    }

}
