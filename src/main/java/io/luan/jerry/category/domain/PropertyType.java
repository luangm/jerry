package io.luan.jerry.category.domain;

import lombok.Getter;

public enum PropertyType {

    Normal(0),
    Key(1), // Key properties defines an SPU
    Sale(2), // Sale properties defines an SKU / Inventory
    Bound(3) // A bound property is one that defined by SPU
    ;

    @Getter
    private int value;

    PropertyType(int value) {
        this.value = value;
    }

    public static PropertyType fromValue(int value) {
        switch (value) {
            case 1:
                return Key;
            case 2:
                return Sale;
            case 3:
                return Bound;
            default:
                return Normal;
        }
    }

}
