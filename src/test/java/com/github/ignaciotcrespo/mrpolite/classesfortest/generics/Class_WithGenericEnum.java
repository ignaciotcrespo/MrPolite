package com.github.ignaciotcrespo.mrpolite.classesfortest.generics;

import java.io.Serializable;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithGenericEnum<T extends Enum<T>> implements Serializable {
    public T status;
}
