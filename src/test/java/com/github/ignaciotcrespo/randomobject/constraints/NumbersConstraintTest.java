package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/21/17.
 */
public class NumbersConstraintTest {

    private PowerField field;
    private Randomizer randomizer = new Randomizer();

    @Before
    public void setUp() throws Exception {
        field = PowerClass.getDeclaredFields(String.class)[0];
    }

    @Test
    public void withNumbers_zero() throws Exception {
        Object value = NumbersConstraint.from(0, 0).apply(field, 32, randomizer);

        assertThat(value).isEqualTo(0);
    }

    @Test
    public void withNumbers_negative() throws Exception {
        Object value = NumbersConstraint.from(Long.MIN_VALUE, Long.MIN_VALUE).apply(field, 240L, randomizer);

        assertThat(value).isEqualTo(Long.MIN_VALUE);
    }


    @Test
    public void withNumbers_positive() throws Exception {
        Object value = NumbersConstraint.from(Long.MAX_VALUE, Long.MAX_VALUE).apply(field, -240L, randomizer);

        assertThat(value).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    public void withNumbers_range() throws Exception {
        NumbersConstraint constraint = NumbersConstraint.from(40, 41);

        assertThat(constraint.apply(field, -500, randomizer)).isIn(40, 41);
        assertThat(constraint.apply(field, 560, randomizer)).isIn(40, 41);
    }

    @Test
    public void withNumbers_range2() throws Exception {
        NumbersConstraint constraint = NumbersConstraint.from(1, 1);

        assertThat(constraint.apply(PowerClass.getDeclaredField(ForTest.class,"number"), -500, randomizer)).isIn(1, 1);
        assertThat(constraint.apply(PowerClass.getDeclaredField(ForTest.class, "number"), 560, randomizer)).isIn(1, 1);
    }

    static class ForTest {
        byte number;
    }

}