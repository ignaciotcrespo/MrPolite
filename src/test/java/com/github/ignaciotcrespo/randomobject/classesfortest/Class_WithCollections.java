package com.github.ignaciotcrespo.randomobject.classesfortest;

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
        assertThat(list).doesNotContainNull().doesNotContain(0).isNotEmpty();
        assertThat(set).doesNotContainNull().isNotEmpty();
        assertThat(queue).doesNotContainNull().doesNotContain(0D).isNotEmpty();
        assertThat(vector).doesNotContainNull().isNotEmpty();
        assertThat(map).isNotEmpty();

        assertThat(list.size()).isBetween(minSize, maxSize);
        assertThat(set.size()).isBetween(minSize, maxSize);
        assertThat(queue.size()).isBetween(minSize, maxSize);
        assertThat(vector.size()).isBetween(minSize, maxSize);
        assertThat(map.size()).isBetween(minSize, maxSize);

        assertTypes(this.list, Integer.class);
        assertTypes(this.set, Date.class);
        assertTypes(this.queue, Double.class);
        assertTypes(this.vector, Calendar.class);
        assertTypes(this.map.keySet(), Byte.class);
        assertTypes(this.map.values(), String.class);
    }

    private void assertTypes(Collection collection, Class<?> clazz) {
        for (Object value: collection) {
            assertThat(value).isInstanceOf(clazz);
        }
    }
}
