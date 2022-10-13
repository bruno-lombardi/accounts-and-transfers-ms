package io.github.brunolombardi.test.mocks;

import io.github.brunolombardi.core.protocols.transactions.MakeTransactionOptions;

import java.math.BigDecimal;

public class MakeTransactionUseCaseMock {
    public static MakeTransactionOptions getMakeTransactionOptions() {
        return MakeTransactionOptions
            .builder()
            .amount(BigDecimal.valueOf(1.00))
            .destinationAccount(new MakeTransactionOptions.AccountParams("123", "654321"))
            .originAccount(new MakeTransactionOptions.AccountParams("123", "456789"))
            .build();
    }
}
