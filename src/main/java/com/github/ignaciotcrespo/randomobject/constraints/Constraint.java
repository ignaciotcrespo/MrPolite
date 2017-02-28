package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/21/17.
 */
public abstract class Constraint {

    public abstract Object apply(Field field, Object value, Randomizer randomizer);

    public abstract boolean canApply(Object value);
}
