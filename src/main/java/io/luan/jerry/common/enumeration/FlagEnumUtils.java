package io.luan.jerry.common.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class FlagEnumUtils {

    public static <E extends Enum<E>> EnumSet<E> decode(int encoded, Class<E> clazz) {

        Map<Integer, E> bitMap = new HashMap<>();
        for (E val : EnumSet.allOf(clazz)) {
            if (val instanceof FlagEnum) {
                bitMap.put(((FlagEnum) val).getBit(), val);
            } else {
                bitMap.put(val.ordinal(), val);
            }
        }

        EnumSet<E> ret = EnumSet.noneOf(clazz);
        int bit = 0;
        for (int i = 1; i != 0; i <<= 1) {
            if ((i & encoded) != 0) {
                ret.add(bitMap.get(bit));
            }
            bit++;
        }

        return ret;
    }

    public static <E extends Enum<E>> int encode(EnumSet<E> set) {
        int ret = 0;

        for (E val : set) {
            if (val instanceof FlagEnum) {
                ret |= 1 << ((FlagEnum) val).getBit();
            } else {
                ret |= 1 << val.ordinal();
            }
        }

        return ret;
    }

}
