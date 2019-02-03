package io.luan.jerry.category.domain;

import lombok.Getter;

public enum BasePropertyState {
    Normal(0),
    Disabled(-1);

    @Getter
    private int value;

    BasePropertyState(int value) {
        this.value = value;
    }

    public static BasePropertyState fromValue(int value) {
        if (value < 0) {
            return Disabled;
        }
        return Normal;
    }
}
