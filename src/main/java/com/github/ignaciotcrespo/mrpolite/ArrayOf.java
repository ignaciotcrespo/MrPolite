package com.github.ignaciotcrespo.mrpolite;

/**
 * Created by crespo on 2/27/17.
 */
class ArrayOf<T> extends CollectionOf<T, T[]> {

    ArrayOf(int size, Class<T> clazz) {
        super(size, clazz);
    }

    public T[] please() {
        return randomObject().randomArray(size, clazz);
    }

}
