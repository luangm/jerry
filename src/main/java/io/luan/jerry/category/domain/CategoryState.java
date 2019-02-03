package io.luan.jerry.category.domain;

import lombok.Getter;

public enum CategoryState {
    Normal(0),
    Disabled(-1);

    @Getter
    private int value;

    CategoryState(int value) {
        this.value = value;
    }

    public static CategoryState fromValue(int value) {
        if (value < 0) {
            return Disabled;
        }
        return Normal;
    }
}
