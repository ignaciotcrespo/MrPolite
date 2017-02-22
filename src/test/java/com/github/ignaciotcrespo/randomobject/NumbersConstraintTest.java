package com.github.ignaciotcrespo.randomobject;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/21/17.
 */
public class NumbersConstraintTest {

    @Test
    public void withNumbers_zero() throws Exception {
        Object value = NumbersConstraint.from(0, 0).apply(32);

        assertThat(value).isEqualTo(0);
    }

    @Test
    public void withNumbers_negative() throws Exception {
        Object value = NumbersConstraint.from(Long.MIN_VALUE, Long.MIN_VALUE).apply(240L);

        assertThat(value).isEqualTo(Long.MIN_VALUE);
    }


    @Test
    public void withNumbers_positive() throws Exception {
        Object value = NumbersConstraint.from(Long.MAX_VALUE, Long.MAX_VALUE).apply(-240L);

        assertThat(value).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    public void withNumbers_range() throws Exception {
        NumbersConstraint constraint = NumbersConstraint.from(40, 41);

        assertThat(constraint.apply(-500)).isIn(40, 41);
        assertThat(constraint.apply(560)).isIn(40, 41);
    }

}