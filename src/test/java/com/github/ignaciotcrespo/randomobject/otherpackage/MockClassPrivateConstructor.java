package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public final class MockClassPrivateConstructor {

    private String text;

    private MockClassPrivateConstructor() {
        // do nothing
    }

    public void assertValidData() {
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
