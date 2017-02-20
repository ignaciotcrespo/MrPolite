package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
public class Processor {

    private final int deepNestedClasses;
    private int deepNestedClassesProcessed = 0;

    public Processor(int deepNestedClasses) {
        this.deepNestedClasses = deepNestedClasses;
    }

    public boolean shouldStopNestedClasses(Field field, Class<?> clazz) {
        boolean equals = field.getType().equals(clazz);
        if (equals) {
            return deepNestedClasses <= deepNestedClassesProcessed++;
        }
        return false;
    }

}
