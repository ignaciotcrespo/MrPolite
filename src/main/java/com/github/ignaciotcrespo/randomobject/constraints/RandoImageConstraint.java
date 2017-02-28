package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/27/17.
 */
class RandoImageConstraint extends Constraint {

    private final String fieldNameRegex;
    private final int width;
    private final int height;

    RandoImageConstraint(String fieldNameRegex, int width, int height) {
        this.fieldNameRegex = fieldNameRegex;
        this.width = width;
        this.height = height;
    }

    @Override
    public Object apply(Field field, Object oldValue, int seed) {
        if (field.getName().matches(fieldNameRegex) && field.getType().isAssignableFrom(String.class)) {
            return "http://lorempixel.com/" + width + "/" + height + "/?rand=" + new Randomizer(seed).nextInt();
        }
        return oldValue;
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof String;
    }

}
