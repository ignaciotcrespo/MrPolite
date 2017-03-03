package com.github.ignaciotcrespo.randomobject.classesfortest;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class Class_Abstract {

    private String text;

    public void assertValidData() {
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }

}
