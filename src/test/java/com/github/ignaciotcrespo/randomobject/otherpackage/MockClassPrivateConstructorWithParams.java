package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public final class MockClassPrivateConstructorWithParams {

    private final Integer num;
    private String text;

    private MockClassPrivateConstructorWithParams(Integer num) {
        this.num = num;
    }

    public void assertValidData() {
        Assertions.assertThat(num).isNotNull();
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
