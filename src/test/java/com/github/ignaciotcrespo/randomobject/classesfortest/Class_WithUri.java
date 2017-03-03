package com.github.ignaciotcrespo.randomobject.classesfortest;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithUri {
    public String text;
    public String name;
    public String imageUri;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Class_WithUri that = (Class_WithUri) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return imageUri != null ? imageUri.equals(that.imageUri) : that.imageUri == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUri != null ? imageUri.hashCode() : 0);
        return result;
    }
}
