package com.github.ignaciotcrespo.mrpolite.classesfortest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithCollections {
    public List<Integer> list;
    public Set<Date> set;
    public Queue<Double> queue;
    public Vector<Calendar> vector;
    public Map<Byte, String> map;

    public void assertValidData(int minSize, int maxSize) {
        assertThat(list).doesNotContainNull().doesNotContain(0).isNotEmpty().hasOnlyElementsOfType(Integer.class);
        assertThat(set).doesNotContainNull().isNotEmpty().hasOnlyElementsOfType(Date.class);
        assertThat(queue).doesNotContainNull().doesNotContain(0D).isNotEmpty().hasOnlyElementsOfType(Double.class);
        assertThat(vector).doesNotContainNull().isNotEmpty().hasOnlyElementsOfType(Calendar.class);
        assertThat(map).isNotEmpty();
        assertThat(map.keySet()).hasOnlyElementsOfType(Byte.class);
        assertThat(map.values()).hasOnlyElementsOfType(String.class);

        assertThat(list.size()).isBetween(minSize, maxSize);
        assertThat(set.size()).isBetween(minSize, maxSize);
        assertThat(queue.size()).isBetween(minSize, maxSize);
        assertThat(vector.size()).isBetween(minSize, maxSize);
        assertThat(map.size()).isBetween(minSize, maxSize);
    }
}
