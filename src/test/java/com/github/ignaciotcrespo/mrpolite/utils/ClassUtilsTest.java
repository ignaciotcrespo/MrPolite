package com.github.ignaciotcrespo.mrpolite.utils;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Tests for {@link ClassUtils}.
 */
public class ClassUtilsTest {

    @Test
    public void primitiveToDefault_notPrimitive_isNull() throws Exception {
        Object def = ClassUtils.primitiveToDefault(String.class);

        assertNull(def);
    }

}