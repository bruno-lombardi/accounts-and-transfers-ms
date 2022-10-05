package io.github.brunolombardi.test.mocks;

import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;

import java.math.BigDecimal;

public class AccountMock {
    public static AccountEntity getAccountEntity() {
        return AccountEntity
                .builder()
                .id("123123121231")
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
    }
}
