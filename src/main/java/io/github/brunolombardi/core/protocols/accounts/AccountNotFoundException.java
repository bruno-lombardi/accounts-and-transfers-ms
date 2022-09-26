package io.github.brunolombardi.core.protocols.accounts;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
