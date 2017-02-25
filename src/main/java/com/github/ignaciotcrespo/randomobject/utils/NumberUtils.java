package com.github.ignaciotcrespo.randomobject.utils;

/**
 * Created by crespo on 2/22/17.
 */
public class NumberUtils {
    public static boolean isNumeric(Object str) {
        return str instanceof Number; // ("" + str).matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Object castedValue(Object object, Object oldValue) {
        if (isNumeric(object)) {
            Number newValue = (Number) object;
            if (oldValue instanceof Byte) {
                return newValue.byteValue();
            } else if (oldValue instanceof Short) {
                return newValue.shortValue();
            } else if (oldValue instanceof Integer) {
                return newValue.intValue();
            } else if (oldValue instanceof Long) {
                return newValue.longValue();
            } else if (oldValue instanceof Float) {
                return newValue.floatValue();
            } else {
                return newValue;
            }
        }
        return object;
    }
}
