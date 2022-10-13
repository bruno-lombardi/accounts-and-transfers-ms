package io.github.brunolombardi.core.protocols.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private String id;
    private String accountBranch;
    private String accountNumber;
    private String holderTaxId;
    private BigDecimal balance;

    public boolean withdraw(BigDecimal value) {
        var newBalance = getBalance().subtract(value);
        var isPositive = newBalance.compareTo(BigDecimal.ZERO) >= 0;
        if (isPositive) {
            setBalance(newBalance);
            return true;
        }
        return false;
    }

    public void deposit(BigDecimal value) {
        var newBalance = getBalance().add(value);
        setBalance(newBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && accountBranch.equals(account.accountBranch) && accountNumber.equals(account.accountNumber) && holderTaxId.equals(account.holderTaxId) && balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountBranch, accountNumber, holderTaxId);
    }
}
