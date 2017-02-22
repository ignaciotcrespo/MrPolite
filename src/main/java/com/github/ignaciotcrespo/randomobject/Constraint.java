package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/21/17.
 */
public abstract class Constraint<T> {

    public abstract T apply(T value);

    public abstract boolean canApply(T value);
}
