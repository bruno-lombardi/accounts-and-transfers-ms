package io.github.brunolombardi.core.protocols.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
        var isPositive = newBalance.compareTo(BigDecimal.ZERO) > 0;
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

}
