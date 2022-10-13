package io.github.brunolombardi.core.protocols.transactions;

public class MakeTransactionErrorException extends RuntimeException {
    public MakeTransactionErrorException(String message) {
        super(message);
    }
}
