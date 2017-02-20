package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassConstructorWithManyParams {

    private final String param1;
    private final MockClassDefaultOtherPackage param2;
    private final int param3;
    private String text;

    public MockClassConstructorWithManyParams(String param1,
                                              MockClassDefaultOtherPackage param2,
                                              byte _byte,
                                              short _short,
                                              int param3,
                                              long _long,
                                              float _float,
                                              double _double
    ) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public void assertValidData() {
        Assertions.assertThat(param1).isNotNull();
        Assertions.assertThat(param2).isNotNull();
        Assertions.assertThat(param3).isNotNull();
        Assertions.assertThat(text).isNotNull().isNotEmpty();
    }
}
