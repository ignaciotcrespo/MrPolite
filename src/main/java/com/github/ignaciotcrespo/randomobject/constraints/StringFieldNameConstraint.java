package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/22/17.
 */
class StringFieldNameConstraint extends Constraint {
    @Override
    public String apply(Field field, Object value, Randomizer randomizer) {
        return field.getName();
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof String;
    }
}
