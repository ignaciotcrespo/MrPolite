package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.enums.Class_WithEnumMap;
import com.github.ignaciotcrespo.randomobject.classesfortest.enums.EnumTwoValues;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_enums_Tests {

    @Test
    public void enumMap() throws Exception {
        Class_WithEnumMap object = one(Class_WithEnumMap.class).please();

        assertThat(object).isNotNull();
        assertThat(object.enumMap).isNotEmpty();
        assertThat(object.enumMap.keySet()).hasOnlyElementsOfType(EnumTwoValues.class);
        assertThat(object.enumMap.values()).hasOnlyElementsOfType(String.class);
    }

}