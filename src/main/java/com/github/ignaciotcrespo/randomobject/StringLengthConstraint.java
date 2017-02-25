package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/21/17.
 */
class StringLengthConstraint extends Constraint {

    private final int len;

    StringLengthConstraint(int len) {
        this.len = len;
    }

    @Override
    public String apply(Field field, Object value) {
        String newValue = (String) value;
        if (newValue.length() > len) {
            newValue = newValue.substring(0, len);
            return newValue;
        }
        return (String) value;
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof String;
    }
}
