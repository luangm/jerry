package io.luan.jerry.category.domain;

import lombok.Getter;

public enum BaseValueState {
    Normal(0),
    Disabled(-1);

    @Getter
    private int value;

    BaseValueState(int value) {
        this.value = value;
    }

    public static BaseValueState fromValue(int value) {
        if (value < 0) {
            return Disabled;
        }
        return Normal;
    }
}
