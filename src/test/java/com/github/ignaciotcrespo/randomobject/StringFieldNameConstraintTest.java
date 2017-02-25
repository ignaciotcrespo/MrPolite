package com.github.ignaciotcrespo.randomobject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/22/17.
 */
public class StringFieldNameConstraintTest {

    private Field[] mFields;

    @Before
    public void setUp() throws Exception {
        mFields = StringFields.class.getDeclaredFields();
    }

    @Test
    public void withStringAsFieldName1() throws Exception {
        StringFieldNameConstraint constraint = new StringFieldNameConstraint();

        String value = constraint.apply(mFields[0], "whatever");

        Assertions.assertThat(value).isEqualTo("text1");
    }

    @Test
    public void withStringAsFieldName2() throws Exception {
        StringFieldNameConstraint constraint = new StringFieldNameConstraint();

        String value = constraint.apply(mFields[1], "whatever");

        Assertions.assertThat(value).isEqualTo("text2");
    }

    private static class StringFields {
        String text1;
        String text2;
    }


}