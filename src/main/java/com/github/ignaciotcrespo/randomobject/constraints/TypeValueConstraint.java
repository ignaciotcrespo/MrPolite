package com.github.ignaciotcrespo.randomobject.constraints;

import java.lang.reflect.Field;

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
    public Object apply(Field field, Object oldValue, int seed) {
        return value;
    }

    @Override
    public boolean canApply(Object value) {
        return clazz.isAssignableFrom(value.getClass());
    }
}
