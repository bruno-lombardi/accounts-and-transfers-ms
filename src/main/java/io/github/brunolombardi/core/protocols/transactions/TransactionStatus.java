package io.github.brunolombardi.core.protocols.transactions;

import lombok.Getter;
import lombok.Setter;

public enum TransactionStatus {
    AWAITING_APPROVAL("AWAITING_APPROVAL"),
    BLOCKED("BLOCKED"),
    FAIL("FAIL"),
    SUCCESS("SUCCESS");

    @Getter
    @Setter
    private String value;

    TransactionStatus(String value) {
        setValue(value);
    }
}
