package io.github.brunolombardi.core.protocols.transactions;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Introspected
public class MakeTransactionOptions {
    @NotNull
    private AccountParams originAccount;

    @NotNull
    private AccountParams destinationAccount;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountParams {
        @NotNull
        @NotBlank
        private String accountBranch;

        @NotNull
        @NotBlank
        private String accountNumber;
    }

}
