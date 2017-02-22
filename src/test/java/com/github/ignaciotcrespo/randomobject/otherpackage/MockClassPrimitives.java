package com.github.ignaciotcrespo.randomobject.otherpackage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/21/17.
 */
public class MockClassPrimitives {

    String _string;
    boolean _boolean;
    byte _byte;
    short _short;
    int _int;
    char _char;
    long _long;
    float _float;
    double _double;

    public void assertNumbers(double num) {
        assertThat(_byte).isEqualTo(((Number) num).byteValue());
        assertThat(_short).isEqualTo(((Number) num).shortValue());
        assertThat(_int).isEqualTo(((Number) num).intValue());
        assertThat(_long).isEqualTo(((Number) num).longValue());
        assertThat(_float).isEqualTo(((Number) num).floatValue());
        assertThat(_double).isEqualTo(((Number) num).doubleValue());
    }

    public void assertNumbers(double min, double max) {
        assertThat(_byte).isBetween(((Number) min).byteValue(), ((Number) max).byteValue());
        assertThat(_short).isBetween(((Number) min).shortValue(), ((Number) max).shortValue());
        assertThat(_int).isBetween(((Number) min).intValue(), ((Number) max).intValue());
        assertThat(_long).isBetween(((Number) min).longValue(), ((Number) max).longValue());
        assertThat(_float).isBetween(((Number) min).floatValue(), ((Number) max).floatValue());
        assertThat(_double).isBetween(((Number) min).doubleValue(), ((Number) max).doubleValue());
    }

    public void assertStringLen(int len) {
        assertThat(_string.length()).isLessThanOrEqualTo(3);
    }
}
