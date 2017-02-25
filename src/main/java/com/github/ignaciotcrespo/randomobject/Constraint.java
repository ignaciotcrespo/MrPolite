package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/21/17.
 */
abstract class Constraint {

    public abstract Object apply(Field field, Object value);

    public abstract boolean canApply(Object value);
}
