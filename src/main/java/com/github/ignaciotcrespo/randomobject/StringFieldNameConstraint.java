package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/22/17.
 */
class StringFieldNameConstraint extends Constraint {
    @Override
    public String apply(Field field, Object value, int seed) {
        return field.getName();
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof String;
    }
}
