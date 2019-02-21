package io.luan.jerry.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    private static final String INNER_SEPARATOR = ":";
    private static final String OUTER_SEPARATOR = ";";

    public static Map<Long, Long> decodeLongLong(String properties) {
        var map = new HashMap<Long, Long>();
        if (properties == null) {
            return map;
        }
        var items = properties.split(OUTER_SEPARATOR);
        for (var item : items) {
            var parts = item.split(INNER_SEPARATOR);
            if (parts.length == 2) {
                var key = Long.parseLong(parts[0]);
                var val = Long.parseLong(parts[1]);
                map.put(key, val);
            }
        }
        return map;
    }

    public static Map<String, String> decodeStringString(String properties) {
        var map = new HashMap<String, String>();
        var items = properties.split(OUTER_SEPARATOR);
        for (var item : items) {
            var parts = item.split(INNER_SEPARATOR);
            if (parts.length == 2) {
                var key = parts[0];
                var val = parts[1];
                map.put(key, val);
            }
        }
        return map;
    }

    public static <K, V> String encode(Map<K, V> map) {
        var sb = new StringBuilder();
        var first = true;
        for (var pair : map.entrySet()) {
            if (!first) {
                sb.append(OUTER_SEPARATOR);
            }
            sb.append(pair.getKey());
            sb.append(INNER_SEPARATOR);
            sb.append(pair.getValue());
            first = false;
        }
        return sb.toString();
    }

    public static boolean Equals(Map<Long, Long> left, Map<Long, Long> right) {
        return left.equals(right);
    }
}
