package com.github.ignaciotcrespo.randomobject.classesfortest;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class Class_WithAnonymousField {

    A anonymous = new A() {

    };

    public void assertValidData() {
        Assertions.assertThat(anonymous).isNotNull();
    }

    static abstract class A {

    }

}
