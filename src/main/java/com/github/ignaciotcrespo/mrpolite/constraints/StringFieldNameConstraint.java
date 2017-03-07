package com.github.ignaciotcrespo.mrpolite.constraints;

import com.github.ignaciotcrespo.mrpolite.utils.PowerField;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

/**
 * Created by crespo on 2/22/17.
 */
class StringFieldNameConstraint extends Constraint {
    @Override
    public String apply(PowerField field, Object value, Randomizer randomizer) {
        return field.getName();
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof String;
    }
}
