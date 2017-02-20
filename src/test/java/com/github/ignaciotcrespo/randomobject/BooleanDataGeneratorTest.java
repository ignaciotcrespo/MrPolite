package com.github.ignaciotcrespo.randomobject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BooleanDataGenerator}.
 */
public class BooleanDataGeneratorTest {

    private BooleanDataGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new BooleanDataGenerator();
    }

    @Test
    public void is() throws Exception {
        assertThat(generator.is(Boolean.class)).isTrue();
        assertThat(generator.is(boolean.class)).isTrue();

        assertThat(generator.is(String.class)).isFalse();
        assertThat(generator.is(Object.class)).isFalse();
    }

    @Test
    public void getValue_true() throws Exception {
        setupRandomizer(true);

        Boolean value = (Boolean) generator.getValue(null, 0);

        assertThat(value).isTrue();
    }

    @Test
    public void getValue_false() throws Exception {
        setupRandomizer(false);

        Boolean value = (Boolean) generator.getValue(null, 0);

        assertThat(value).isFalse();
    }

    private void setupRandomizer(boolean value) {
        generator.mRandomizer = mock(Randomizer.class);
        when(generator.mRandomizer.nextBoolean()).thenReturn(value);
    }

}