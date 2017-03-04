package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BooleanDataGenerator}.
 */
public class BooleanDataGeneratorTest {

    private BooleanDataGenerator generator;
    private Randomizer randomizer;

    @Before
    public void setUp() throws Exception {
        randomizer = mock(Randomizer.class);
        generator = new BooleanDataGenerator(randomizer);
    }

    @Test
    public void is() throws Exception {
        assertThat(generator.canProcess(Boolean.class)).isTrue();
        assertThat(generator.canProcess(boolean.class)).isTrue();

        assertThat(generator.canProcess(String.class)).isFalse();
        assertThat(generator.canProcess(Object.class)).isFalse();
    }

    @Test
    public void getValue_true() throws Exception {
        setupRandomizer(true);

        Boolean value = (Boolean) generator.getValue(null, null, null);

        assertThat(value).isTrue();
    }

    @Test
    public void getValue_false() throws Exception {
        setupRandomizer(false);

        Boolean value = (Boolean) generator.getValue(null, null, null);

        assertThat(value).isFalse();
    }

    private void setupRandomizer(boolean value) {
        when(randomizer.nextBoolean()).thenReturn(value);
    }

}