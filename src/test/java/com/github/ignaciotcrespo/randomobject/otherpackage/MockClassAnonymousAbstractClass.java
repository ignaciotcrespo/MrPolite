package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassAnonymousAbstractClass {

    A anonymous = new A() {

    };

    public void assertValidData() {
        Assertions.assertThat(anonymous).isNotNull();
    }

    static abstract class A {

    }

}
