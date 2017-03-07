package com.github.ignaciotcrespo.mrpolite.classesfortest.generics;

import java.util.List;
import java.util.Set;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_genericWildcard {

    public List<Class_WithGenerics_T<? extends Set>> list;
    public List<Class_WithGenerics_T<Class_WithGenerics_T<? extends Set>>> listNested;

}
