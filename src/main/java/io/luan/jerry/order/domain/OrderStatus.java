package io.luan.jerry.order.domain;

import lombok.Getter;

public enum OrderStatus {

    Created(0),
    Enabled(1),
    Canceled(-1),
    Exception(-2);

    @Getter
    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public static OrderStatus fromValue(int value) {
        switch (value) {
            case 0:
                return Created;
            case 1:
                return Enabled;
            case -1:
                return Canceled;
            default:
                return Exception;
        }
    }
}
