/**
 * The MIT License
 * <p>
 * Copyright (c) 2017, Ignacio Tomas Crespo (itcrespo@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.generators.DataGenerator;
import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_withDataGenerator_Tests {


    @Test
    public void withDataGenerator() throws Exception {
        AnimalForTest object = MrPolite.one(AnimalForTest.class)
                .withDataGenerator(new DataGenerator() {
                    @Override
                    public boolean canProcess(Class<?> type) {
                        return type.equals(Animal.class);
                    }

                    @Override
                    public Object getValue(PowerClass clazz, Type[] generics) {
                        return new Cat();
                    }
                })
                .please();

        assertThat(object.animal).isInstanceOf(Cat.class);
    }

    @Test
    public void withDataGenerator_newGenerator_morePriority() throws Exception {
        String object = MrPolite.one(String.class)
                .withDataGenerator(new DataGenerator() {
                    @Override
                    public boolean canProcess(Class<?> type) {
                        return type.equals(String.class);
                    }

                    @Override
                    public Object getValue(PowerClass clazz, Type[] generics) {
                        return "hi!";
                    }
                })
                .please();

        assertThat(object).isEqualTo("hi!");
    }

    static class AnimalForTest {
        Animal animal;
    }

    static class Animal {
        String name;

        public Animal(String name) {
            this.name = name;
        }
    }

    static class Cat extends Animal {
        Cat() {
            super("cat");
        }
    }
}