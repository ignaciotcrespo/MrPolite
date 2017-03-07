package com.github.ignaciotcrespo.mrpolite.classesfortest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/21/17.
 */
public class Class_WithPrimitives {

    String _string;
    String _string2;
    boolean _boolean;
    byte _byte;
    short _short;
    int _int;
    char _char;
    long _long;
    float _float;
    double _double;

    String _stringWithDefaultValue = "def value";
    int _intWithDefaultValue = 999;

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
        assertThat(_string2.length()).isLessThanOrEqualTo(3);
    }

    public void assertString(String... value) {
        assertThat(_string).isEqualTo(value[0]);
        if (value.length > 1) {
            assertThat(_string2).isEqualTo(value[1]);
        }
    }

    public void assertByte(byte value) {
        assertThat(_byte).isEqualTo(value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Class_WithPrimitives that = (Class_WithPrimitives) o;

        if (_boolean != that._boolean) return false;
        if (_byte != that._byte) return false;
        if (_short != that._short) return false;
        if (_int != that._int) return false;
        if (_char != that._char) return false;
        if (_long != that._long) return false;
        if (Float.compare(that._float, _float) != 0) return false;
        if (Double.compare(that._double, _double) != 0) return false;
        if (_intWithDefaultValue != that._intWithDefaultValue) return false;
        if (_string != null ? !_string.equals(that._string) : that._string != null) return false;
        if (_string2 != null ? !_string2.equals(that._string2) : that._string2 != null) return false;
        return _stringWithDefaultValue != null ? _stringWithDefaultValue.equals(that._stringWithDefaultValue) : that._stringWithDefaultValue == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _string != null ? _string.hashCode() : 0;
        result = 31 * result + (_string2 != null ? _string2.hashCode() : 0);
        result = 31 * result + (_boolean ? 1 : 0);
        result = 31 * result + (int) _byte;
        result = 31 * result + (int) _short;
        result = 31 * result + _int;
        result = 31 * result + (int) _char;
        result = 31 * result + (int) (_long ^ (_long >>> 32));
        result = 31 * result + (_float != +0.0f ? Float.floatToIntBits(_float) : 0);
        temp = Double.doubleToLongBits(_double);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (_stringWithDefaultValue != null ? _stringWithDefaultValue.hashCode() : 0);
        result = 31 * result + _intWithDefaultValue;
        return result;
    }

    @Override
    public String toString() {
        return "Class_WithPrimitives{" +
                "_string='" + _string + '\'' +
                ", _string2='" + _string2 + '\'' +
                ", _boolean=" + _boolean +
                ", _byte=" + _byte +
                ", _short=" + _short +
                ", _int=" + _int +
                ", _char=" + _char +
                ", _long=" + _long +
                ", _float=" + _float +
                ", _double=" + _double +
                ", _stringWithDefaultValue='" + _stringWithDefaultValue + '\'' +
                ", _intWithDefaultValue=" + _intWithDefaultValue +
                '}';
    }

    public void assertStringNull() {
        assertThat(_string).isNull();
        assertThat(_string2).isNull();
    }

    public void assertNumbersRandom() {
        assertThat(_byte).isNotEqualTo((byte) 0);
        assertThat(_short).isNotEqualTo((short) 0);
        assertThat(_int).isNotEqualTo(0);
        assertThat(_long).isNotEqualTo(0L);
        assertThat(_float).isNotEqualTo(0F);
        assertThat(_double).isNotEqualTo(0D);
    }

    public void assertNotOverride() {
        assertThat(_stringWithDefaultValue).isEqualTo("def value");
        assertThat(_intWithDefaultValue).isEqualTo(999);
    }

    public void assertOverride() {
        assertThat(_stringWithDefaultValue).isNotNull().isNotEqualTo("def value");
        assertThat(_intWithDefaultValue).isNotNull().isNotEqualTo(0).isNotEqualTo(999);
    }
}
