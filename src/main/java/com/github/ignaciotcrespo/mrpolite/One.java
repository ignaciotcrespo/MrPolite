package com.github.ignaciotcrespo.mrpolite;

/**
 * Created by crespo on 2/27/17.
 */
class One<T> extends PoliteDesireForClass<T, T> {

    One(Class<T> clazz) {
        super(clazz);
    }

    public T please() {
        return randomObject().randomObject(clazz);
    }


}
