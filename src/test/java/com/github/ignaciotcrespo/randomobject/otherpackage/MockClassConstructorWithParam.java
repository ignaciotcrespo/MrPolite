package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassConstructorWithParam {

    private final String param1;
    private String text;

    public MockClassConstructorWithParam(String param1) {
        this.param1 = param1;
    }

    public void assertValidData() {
        Assertions.assertThat(param1).isNotNull();
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
