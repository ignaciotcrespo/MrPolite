package com.github.ignaciotcrespo.randomobject;

import java.util.List;

/**
 * Created by crespo on 2/27/17.
 */
class ListOf<T> extends CollectionOf<T, List<T>> {

    ListOf(int size, Class<T> clazz) {
        super(size, clazz);
    }

    public List<T> please() {
        return randomObject().fill(size, clazz);
    }

}
