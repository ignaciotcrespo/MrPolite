package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/27/17.
 */
abstract class PoliteDesireForClass<T, V> extends PoliteDesireImpl<T, V> {

    protected final Class<T> clazz;

    PoliteDesireForClass(Class<T> clazz) {
        this.clazz = clazz;
    }

}
