package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.NumberUtils;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

import static com.github.ignaciotcrespo.randomobject.utils.NumberUtils.isNumeric;

/**
 * Created by crespo on 2/22/17.
 */
class FieldNameRegexConstraint extends Constraint {

    private final String fieldNameRegex;
    private final Object value;

    FieldNameRegexConstraint(String fieldNameRegex, Object value) {
        this.fieldNameRegex = fieldNameRegex;
        this.value = value;
    }

    @Override
    public Object apply(Field field, Object oldValue, Randomizer randomizer) {
        if (this.value == null) {
            if (field.getType().isPrimitive()) {
                return 0;
            } else {
                return oldValue;
            }
        }
        if (field.getName().matches(fieldNameRegex) && isAssignableFrom(field, oldValue)) {
            if (NumberUtils.isNumeric(this.value)) {
                return NumberUtils.castedValue(this.value, oldValue);
            }
            return this.value;
        }

        return oldValue;
    }

    private boolean isAssignableFrom(Field field, Object oldValue) {
        Class<?> type = field.getType();
        return type.isPrimitive() && isNumeric(oldValue)
                || type.isAssignableFrom(this.value.getClass());
    }

    @Override
    public boolean canApply(Object value) {
        return true;
    }
}
