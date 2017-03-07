package com.github.ignaciotcrespo.mrpolite.classesfortest;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public final class Class_WithPrivateConstructorWithParams {

    private final Integer num;
    private String text;

    private Class_WithPrivateConstructorWithParams(Integer num) {
        this.num = num;
    }

    public void assertValidData() {
        Assertions.assertThat(num).isNotNull();
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
