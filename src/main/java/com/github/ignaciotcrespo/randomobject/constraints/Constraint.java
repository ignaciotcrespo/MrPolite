package com.github.ignaciotcrespo.randomobject.constraints;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/21/17.
 */
public abstract class Constraint {

    public abstract Object apply(Field field, Object value, int seed);

    public abstract boolean canApply(Object value);
}
