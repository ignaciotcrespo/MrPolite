package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/27/17.
 */
abstract class CollectionOf<T, V> extends PoliteDesireForClass<T, V> {
    final int size;

    CollectionOf(int size, Class<T> clazz) {
        super(clazz);
        this.size = size;
    }

}
