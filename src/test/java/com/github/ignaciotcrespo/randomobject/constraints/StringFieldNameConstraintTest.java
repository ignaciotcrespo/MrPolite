package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by crespo on 2/22/17.
 */
public class StringFieldNameConstraintTest {

    private PowerField[] mFields;
    private Randomizer randomizer = new Randomizer();

    @Before
    public void setUp() throws Exception {
        mFields = PowerClass.getDeclaredFields(StringFields.class);
    }

    @Test
    public void withStringAsFieldName1() throws Exception {
        StringFieldNameConstraint constraint = new StringFieldNameConstraint();

        String value = constraint.apply(mFields[0], "whatever", randomizer);

        Assertions.assertThat(value).isEqualTo("text1");
    }

    @Test
    public void withStringAsFieldName2() throws Exception {
        StringFieldNameConstraint constraint = new StringFieldNameConstraint();

        String value = constraint.apply(mFields[1], "whatever", randomizer);

        Assertions.assertThat(value).isEqualTo("text2");
    }

    private static class StringFields {
        String text1;
        String text2;
    }


}