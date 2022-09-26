package io.github.brunolombardi.infra.mongodb.entities;

import io.github.brunolombardi.core.protocols.accounts.Account;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@MappedEntity
public class AccountEntity {

    @Id
    @GeneratedValue
    private String id;

    @NonNull
    @NotBlank
    private String accountBranch;

    @NonNull
    @NotBlank
    private String accountNumber;

    @NonNull
    @NotBlank
    private String holderTaxId;

    @NonNull
    private BigDecimal balance;

    public Account toAccount() {
        return Account
                .builder()
                .id(getId())
                .accountBranch(getAccountBranch())
                .accountNumber(getAccountNumber())
                .holderTaxId(getHolderTaxId())
                .balance(getBalance())
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
