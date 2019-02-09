package io.luan.jerry.category.domain;

import io.luan.jerry.common.enumeration.FlagEnum;
import io.luan.jerry.common.enumeration.FlagEnumUtils;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;

public enum PropertyOption implements FlagEnum {

    Required(0),
    Multi(1),
    Enumerable(2),
    Mutable(3);

    @Getter
    private int bit;

    PropertyOption(int value) {
        this.bit = value;
    }

    public static EnumSet<PropertyOption> fromValue(int value) {
        return FlagEnumUtils.decode(value, PropertyOption.class);
    }

    public static EnumSet<PropertyOption> fromValues(List<Integer> options) {
        var set = EnumSet.noneOf(PropertyOption.class);
        for (var item : options) {
            var prop = PropertyOption.fromBit(item);
            set.add(prop);
        }
        return set;
    }

    private static PropertyOption fromBit(int bit) {
        switch (bit) {
            case 0:
                return Required;
            case 1:
                return Multi;
            case 2:
                return Enumerable;
            case 3:
                return Mutable;
            default:
                throw new IllegalArgumentException();
        }
    }
}
