package com.github.ignaciotcrespo.mrpolite;

import java.util.List;

/**
 * Created by crespo on 2/25/17.
 */
public class MrPolite {

    public static <T> PoliteDesire<T, T> one(Class<T> clazz) {
        return new One<T>(clazz);
    }

    public static <T> PoliteDesire<T, List<T>> aListOf(int size, Class<T> clazz) {
        return new ListOf<T>(size, clazz);
    }

    public static <T> PoliteDesire<T, T[]> anArrayOf(int size, Class<T> clazz) {
        return new ArrayOf<T>(size, clazz);
    }

    public static PoliteDesire<Object, Object> change(Object object) {
        return new Change<Object>(object);
    }
}
