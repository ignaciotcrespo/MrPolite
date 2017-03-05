package com.github.ignaciotcrespo.randomobject.constraints;

import com.github.ignaciotcrespo.randomobject.utils.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

/**
 * Created by crespo on 2/21/17.
 */
public abstract class Constraint {

    public abstract Object apply(PowerField field, Object value, Randomizer randomizer);

    public abstract boolean canApply(Object value);
}
