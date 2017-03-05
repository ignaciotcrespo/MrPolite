package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;

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

    boolean shouldStopNestedSameClasses(Field field, PowerClass clazz) {
        boolean equals = clazz.isFor(field);
        return equals && deepNestedSameClasses <= deepNestedSameClassesProcessed++;
    }

}
