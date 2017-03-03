package com.github.ignaciotcrespo.randomobject.classesfortest;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class Class_ConstructorWithParam {

    private final String _string;
    private String _stringNotParam;

    public Class_ConstructorWithParam(String _string) {
        this._string = _string;
    }

    public void assertValidData() {
        Assertions.assertThat(_string).isNotNull();
        Assertions.assertThat(_stringNotParam).isNotEmpty();
    }
}
