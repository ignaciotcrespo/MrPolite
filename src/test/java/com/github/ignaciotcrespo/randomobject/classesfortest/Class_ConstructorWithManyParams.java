package com.github.ignaciotcrespo.randomobject.classesfortest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class Class_ConstructorWithManyParams {

    private final String _string;
    private final Class_Default object;
    private final int _int;
    private final byte _byte;
    private final short _short;
    private final long _long;
    private final float _float;
    private final double _double;
    private String _stringNotParam;

    public Class_ConstructorWithManyParams(String _string,
                                           Class_Default object,
                                           byte _byte,
                                           short _short,
                                           int _int,
                                           long _long,
                                           float _float,
                                           double _double
    ) {
        this._string = _string;
        this.object = object;
        this._int = _int;
        this._byte = _byte;
        this._short = _short;
        this._long = _long;
        this._float = _float;
        this._double = _double;
    }

    public void assertValidData() {
        // all params must be initialized with default non null values
        assertThat(_string).isNotNull();
        assertThat(object).isNotNull();
        assertThat(_byte).isNotNull();
        assertThat(_short).isNotNull();
        assertThat(_int).isNotNull();
        assertThat(_long).isNotNull();
        assertThat(_float).isNotNull();
        assertThat(_double).isNotNull();
        assertThat(_stringNotParam).isNotEmpty();
    }
}
