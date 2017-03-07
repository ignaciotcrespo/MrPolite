package com.github.ignaciotcrespo.mrpolite.classesfortest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class Class_WithAbstractField {

    ClassAbstract _abstract;

    public void assertValidData() {
        assertThat(_abstract).isNull();
    }

    static abstract class ClassAbstract {

    }

}
