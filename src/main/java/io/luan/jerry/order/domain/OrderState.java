package io.luan.jerry.order.domain;

import lombok.Getter;

public enum OrderState {

    Created(0),
    Enabled(1),
    Canceled(-1),
    Exception(-2);

    @Getter
    private int value;

    OrderState(int value) {
        this.value = value;
    }

    public static OrderState fromValue(int value) {
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
