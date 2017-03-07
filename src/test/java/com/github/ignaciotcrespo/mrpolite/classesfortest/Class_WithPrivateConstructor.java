package com.github.ignaciotcrespo.mrpolite.classesfortest;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public final class Class_WithPrivateConstructor {

    private String text;

    private Class_WithPrivateConstructor() {
        // do nothing
    }

    public void assertValidData() {
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
