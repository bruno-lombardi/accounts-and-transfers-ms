package io.github.brunolombardi.core.protocols.accounts;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTests {

    @Test
    void shouldNotWithdrawIfBalanceIsNegative() {
        var account = Account
                .builder()
                .balance(BigDecimal.valueOf(0))
                .build();
        var isSuccessful = account.withdraw(BigDecimal.valueOf(10.0));
        assertFalse(isSuccessful);
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void shouldWithdrawIfBalanceIsNonNegative() {
        var account = Account
                .builder()
                .balance(BigDecimal.valueOf(1.0))
                .build();
        var isSuccessful = account.withdraw(BigDecimal.valueOf(0.99));
        assertTrue(isSuccessful);
        assertEquals(BigDecimal.valueOf(0.01), account.getBalance());
    }

    @Test
    void shouldAddToBalanceWhenDeposit() {
        var account = Account
                .builder()
                .balance(BigDecimal.valueOf(0.0))
                .build();
        account.deposit(BigDecimal.valueOf(0.1));
        assertEquals(BigDecimal.valueOf(0.1), account.getBalance());
    }
}