package com.github.ignaciotcrespo.randomobject.classesfortest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithCollections_Nested {
    public List<Set<String>> list;
    public Set<Vector<Long>> set;
    public Map<Integer, List<Date>> map;

    public void assertValidData(int minSize, int maxSize) {
        assertCol(list, Set.class, String.class, minSize, maxSize);
        assertCol(set, Vector.class, Long.class, minSize, maxSize);
        assertThat(map).isNotEmpty();
        assertCol(map.keySet(), Integer.class, null, minSize, maxSize);
        assertCol(map.values(), List.class, Date.class, minSize, maxSize);
    }

    private void assertCol(Collection col, Class<?> type, Class<?> clazz, int minSize, int maxSize) {
        assertThat(col).doesNotContainNull().isNotEmpty();
        assertThat(col.size()).isBetween(minSize, maxSize);
        for (Object value : col) {
            assertThat(value).isInstanceOf(type);
            if (clazz != null) {
                assertCol((Collection) value, clazz, null, minSize, maxSize);
            }
        }
    }

}