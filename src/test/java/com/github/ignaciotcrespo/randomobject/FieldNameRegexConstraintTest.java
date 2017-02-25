package com.github.ignaciotcrespo.randomobject;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by crespo on 2/22/17.
 */
public class FieldNameRegexConstraintTest {

    @Test
    public void apply_wrongValue_shouldNotChange() throws Exception {
        FieldNameRegexConstraint constraint = new FieldNameRegexConstraint("text", 1);

        Object value = constraint.apply(ClassForTest.class.getDeclaredField("text"), "anything");

        Assertions.assertThat(value).isEqualTo("anything");
    }

    @Test
    public void apply_okValue_shouldChange() throws Exception {
        FieldNameRegexConstraint constraint = new FieldNameRegexConstraint("text", "1");

        Object value = constraint.apply(ClassForTest.class.getDeclaredField("text"), "anything");

        Assertions.assertThat(value).isEqualTo("1");
    }

    @Test
    public void apply_regexOk_shouldChange() throws Exception {
        FieldNameRegexConstraint constraint = new FieldNameRegexConstraint("t\\w+", "1");

        Object value = constraint.apply(ClassForTest.class.getDeclaredField("text"), "anything");
        Object value2 = constraint.apply(ClassForTest.class.getDeclaredField("text2"), "anything");

        Assertions.assertThat(value).isEqualTo("1");
        Assertions.assertThat(value2).isEqualTo("1");
    }

    static class ClassForTest {
        String text;
        String text2;
        int number;
    }
}