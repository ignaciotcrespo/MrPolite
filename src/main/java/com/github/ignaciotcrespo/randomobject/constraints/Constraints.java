package com.github.ignaciotcrespo.randomobject.constraints;

/**
 * Created by crespo on 2/28/17.
 */
public class Constraints {
    public static Constraint numbersConstraint(Number min, Number max) {
        return NumbersConstraint.from(min, max);
    }

    public static Constraint stringLengthConstraint(int len) {
        return new StringLengthConstraint(len);
    }

    public static Constraint stringFieldNameConstraint() {
        return new StringFieldNameConstraint();
    }

    public static Constraint fieldNameRegexConstraint(String fieldNameRegex, Object value) {
        return new FieldNameRegexConstraint(fieldNameRegex, value);
    }

    public static <K> Constraint typeValueConstraint(Class<K> clazz, K value) {
        return new TypeValueConstraint<>(clazz, value);
    }

    public static Constraint randoImageConstraint(String fieldNameRegex, int width, int height) {
        return new RandoImageConstraint(fieldNameRegex, width, height);
    }
}
