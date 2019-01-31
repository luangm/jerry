package io.luan.jerry.common.utils;

import java.util.List;

public class ArrayUtils {

    public static String arrayToString(List<Long> array, String delimeter) {
        var sb = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            if (i > 0) {
                sb.append(delimeter);
            }
            sb.append(array.get(i));
        }
        return sb.toString();
    }
}
