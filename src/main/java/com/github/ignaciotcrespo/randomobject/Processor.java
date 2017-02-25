package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class Processor {

    private final int deepNestedSameClasses;
    private int deepNestedSameClassesProcessed = 0;

    Processor(int deepNestedSameClasses) {
        this.deepNestedSameClasses = deepNestedSameClasses;
    }

    boolean shouldStopNestedSameClasses(Field field, Class<?> clazz) {
        boolean equals = field.getType().equals(clazz);
        return equals && deepNestedSameClasses <= deepNestedSameClassesProcessed++;
    }

}
