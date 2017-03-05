package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/27/17.
 */
class One<T> extends PoliteDesireImpl<T, T> {

    One(Class<T> clazz) {
        super(clazz);
    }

    public T please() {
        return randomObject().populate(clazz);
    }


}
