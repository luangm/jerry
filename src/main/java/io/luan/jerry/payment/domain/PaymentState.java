package io.luan.jerry.payment.domain;

import lombok.Getter;

public enum PaymentState {

    Created(0), // Not yet paid
    Paid(1), // already paid
    Refunded(2), // Refunded
    Canceled(-1),
    Exception(-2);

    @Getter
    private int value;

    PaymentState(int value) {
        this.value = value;
    }

    public static PaymentState fromValue(int value) {
        switch (value) {
            case 0:
                return Created;
            case 1:
                return Paid;
            case 2:
                return Refunded;
            case -1:
                return Canceled;
            default:
                return Exception;
        }
    }
}
