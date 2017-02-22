package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/21/17.
 */
public class StringLengthConstraint extends Constraint<String> {

    private final int len;

    public StringLengthConstraint(int len) {
        this.len = len;
    }

    @Override
    public String  apply(String value) {
        String newValue = value;
        if (newValue.length() > len) {
            newValue = newValue.substring(0, len);
            return newValue;
        }
        return value;
    }

    @Override
    public boolean canApply(String value) {
        return value instanceof String;
    }
}
