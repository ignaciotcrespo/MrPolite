package com.github.ignaciotcrespo.mrpolite.constraints;

import com.github.ignaciotcrespo.mrpolite.utils.PowerField;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

/**
 * Created by crespo on 2/23/17.
 */
class TypeValueConstraint<K> extends Constraint {

    private final Class<K> clazz;
    private final K value;

    TypeValueConstraint(Class<K> clazz, K value) {
        this.clazz = clazz;
        this.value = value;
    }

    @Override
    public Object apply(PowerField field, Object oldValue, Randomizer randomizer) {
        return value;
    }

    @Override
    public boolean canApply(Object value) {
        return clazz.isAssignableFrom(value.getClass());
    }
}
