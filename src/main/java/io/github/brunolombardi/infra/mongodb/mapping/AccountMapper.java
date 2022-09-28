package io.github.brunolombardi.infra.mongodb.mapping;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.github.brunolombardi.infra.mongodb.entities.AccountEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountMapper {
    public static Account toAccount(AccountEntity accountEntity) {
        return Account
                .builder()
                .id(accountEntity.getId())
                .accountBranch(accountEntity.getAccountBranch())
                .accountNumber(accountEntity.getAccountNumber())
                .holderTaxId(accountEntity.getHolderTaxId())
                .balance(accountEntity.getBalance())
                .build();
    }

    public static AccountEntity fromAccount(Account account) {
        return AccountEntity
                .builder()
                .id(account.getId())
                .accountBranch(account.getAccountBranch())
                .accountNumber(account.getAccountNumber())
                .holderTaxId(account.getHolderTaxId())
                .balance(account.getBalance())
                .build();
    }
}
