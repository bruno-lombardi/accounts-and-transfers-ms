package io.github.brunolombardi.test.mocks;

import io.github.brunolombardi.core.protocols.accounts.Account;
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

    public static Account getAccount() {
        return Account
                .builder()
                .id("12312312313")
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
    }

    public static Account getAccountWithId(String id) {
        return Account
                .builder()
                .id(id)
                .accountBranch("123")
                .accountNumber("123")
                .balance(BigDecimal.valueOf(100.0))
                .holderTaxId("12312312312")
                .build();
    }
}
