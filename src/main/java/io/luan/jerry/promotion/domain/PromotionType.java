package io.luan.jerry.promotion.domain;

public enum PromotionType {

    SingleItem(1);

    private int value;

    PromotionType(int value) {
        this.value = value;
    }

    public static PromotionType fromValue(int value) {
        return SingleItem;
    }

    public int getValue() {
        return value;
    }
}
