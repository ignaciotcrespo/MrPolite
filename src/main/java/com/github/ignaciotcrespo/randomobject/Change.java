package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/27/17.
 */
class Change<T> extends PoliteDesireImpl<T, T> {

    private final T object;

    Change(T object) {
        this.object = object;
    }

    public T please() {
        this.overrideValues(false);
        return randomObject().randomObject(object);
    }


}
