package io.luan.jerry.cart.domain;

import lombok.Getter;

public enum CartItemState {

    Normal(0),
    Deleted(-1);

    @Getter
    private int value;

    CartItemState(int value) {
        this.value = value;
    }

    public static CartItemState fromValue(int value) {
        if (value < 0) {
            return Deleted;
        }
        return Normal;
    }
}
