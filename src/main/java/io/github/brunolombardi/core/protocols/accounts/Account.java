package io.github.brunolombardi.core.protocols.accounts;

import io.github.brunolombardi.core.protocols.transactions.AccountTransaction;
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
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
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
