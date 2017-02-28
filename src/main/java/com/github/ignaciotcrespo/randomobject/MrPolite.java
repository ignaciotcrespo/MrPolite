package com.github.ignaciotcrespo.randomobject;

import java.util.List;

/**
 * Created by crespo on 2/25/17.
 */
public class MrPolite {

    public static <T> PoliteDesire<T, T> one(Class<T> clazz) {
        return new One<>(clazz);
    }

    public static <T> PoliteDesire<T, List<T>> aListOf(int size, Class<T> clazz) {
        return new ListOf<>(size, clazz);
    }

    public static <T> PoliteDesire<T, T[]> anArrayOf(int size, Class<T> clazz) {
        return new ArrayOf<>(size, clazz);
    }

}
