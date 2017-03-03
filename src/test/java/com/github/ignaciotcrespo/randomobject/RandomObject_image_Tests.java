package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithUri;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.randomobject.MrPolite.aListOf;
import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_image_Tests {

    @Test
    public void withRandomImageLink() throws Exception {
        One<Class_WithUri> one = (One<Class_WithUri>) one(Class_WithUri.class)
                .withFieldImageLink(".*[uU]ri.*", 300, 200);
        one.seed = 1234;
        Class_WithUri object = one.please();

        assertThat(object.imageUri).isEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
        assertThat(object.text).isNotEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
        assertThat(object.name).isNotEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
    }

    @Test
    public void withRandomImageLink_different() throws Exception {
        Class_WithUri object = one(Class_WithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();
        Class_WithUri object2 = one(Class_WithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();

        assertThat(object.imageUri).isNotEqualTo(object2.imageUri);
    }

    @Test
    public void withRandomImageLink_list_different() throws Exception {
        List<Class_WithUri> list = aListOf(3, Class_WithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();

        assertThat(list.get(0).imageUri).isNotEqualTo(list.get(1).imageUri);
        assertThat(list.get(1).imageUri).isNotEqualTo(list.get(2).imageUri);
    }

}