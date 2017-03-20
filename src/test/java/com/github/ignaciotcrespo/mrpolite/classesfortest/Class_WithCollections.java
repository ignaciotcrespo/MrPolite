package com.github.ignaciotcrespo.mrpolite.classesfortest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class for test, with collections inside.
 */
public class Class_WithCollections {
    public List<Integer> list;
    public Set<Date> set;
    private Queue<Double> queue;
    private Vector<Calendar> vector;
    private Map<Byte, String> map;
    private Iterable<String> iterable;
    private Iterator<String> iterator;
    private Enumeration<String> enumeration;

    public void assertValidData(int minSize, int maxSize) {
        assertThat(list).doesNotContainNull().doesNotContain(0).isNotEmpty().hasOnlyElementsOfType(Integer.class);
        assertThat(set).doesNotContainNull().isNotEmpty().hasOnlyElementsOfType(Date.class);
        assertThat(queue).doesNotContainNull().doesNotContain(0D).isNotEmpty().hasOnlyElementsOfType(Double.class);
        assertThat(vector).doesNotContainNull().isNotEmpty().hasOnlyElementsOfType(Calendar.class);
        assertThat(iterable).doesNotContainNull().isNotEmpty().hasOnlyElementsOfType(String.class);
        assertThat(enumeration).isNotNull();
        assertThat(map).isNotEmpty();
        assertThat(map.keySet()).hasOnlyElementsOfType(Byte.class);
        assertThat(map.values()).hasOnlyElementsOfType(String.class);

        assertThat(list.size()).isBetween(minSize, maxSize);
        assertThat(set.size()).isBetween(minSize, maxSize);
        assertThat(queue.size()).isBetween(minSize, maxSize);
        assertThat(vector.size()).isBetween(minSize, maxSize);
        assertThat(sizeOf(iterable)).isBetween(minSize, maxSize);
        assertThat(sizeOf(iterator)).isBetween(minSize, maxSize);
        assertThat(sizeOf(enumeration)).isBetween(minSize, maxSize);
        assertThat(map.size()).isBetween(minSize, maxSize);
    }

    private static int sizeOf(Iterable iterable) {
        int count = 0;
        for (Object ignored : iterable) {
            count++;
        }
        return count;
    }

    private static int sizeOf(Iterator<String> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            assertThat(iterator.next()).isNotEmpty();
            count++;
        }
        return count;
    }

    private static int sizeOf(Enumeration<String> enumeration) {
        int count = 0;
        while (enumeration.hasMoreElements()) {
            assertThat(enumeration.nextElement()).isNotEmpty();
            count++;
        }
        return count;
    }
}
