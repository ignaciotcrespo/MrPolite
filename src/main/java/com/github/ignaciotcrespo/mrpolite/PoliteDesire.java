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

/**
 * Represents how the data must be generated, and also allows to build the requested object using {@link #please()}.
 */
public interface PoliteDesire<C, V> {

    /**
     * Build the object
     *
     * @return the requested object
     */
    V please();

    /**
     * By default Mr Polite will search 3 levels in the fields hierarchy to create objects.
     *
     * @param depth how many levels
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withDepth(int depth);

    /**
     * +
     * A range for all the fields containing numbers.
     *
     * @param min the minimum
     * @param max the maximum
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withNumberRange(Number min, Number max);

    /**
     * The maximum length for all the generated strings.
     *
     * @param len the maximum length
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withStringsMaxLength(int len);

    /**
     * Generate strings using the field name. For instance, a field
     * <p>
     * <code>
     * String name;
     * </code>
     * </p>
     * will contain the value "name".
     *
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withFieldNamesInStrings();

    /**
     * Set a specific value in some fields, searching by name.
     *
     * @param fieldNameRegex A regular expression to identify the field by name.
     * @param value          the value to set
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withFieldEqualTo(String fieldNameRegex, Object value);

    /**
     * Set a specific value in fields with a specific type.
     *
     * @param clazz the type of the field
     * @param value the value to set
     * @param <K>   the class and the value must be of the same type
     * @return the same {@link PoliteDesire}
     */
    <K> PoliteDesire<C, V> withClassEqualTo(Class<K> clazz, K value);

    /**
     * Set a http link to an image, on fields searching by name. It creates a link using the excellent service "http://lorempixel.com".
     *
     * @param fieldNameRegex A regular expression to identify the field by name.
     * @param width          the image width
     * @param height         the image height
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withFieldImageLink(String fieldNameRegex, int width, int height);

    /**
     * A seed to randomize the generated values. Useful when you want to generate the same values, for instance, for testing purposes.
     *
     * @param seed the seed value
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withSeed(int seed);

    /**
     * Mr Polite will try to create collections/arrays. Define here a range for the desired size.
     *
     * @param min the minimum size
     * @param max the maximum size
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withCollectionSizeRange(int min, int max);

    /**
     * Exclude fields searching by name. The excluded fields will not be changed.
     *
     * @param fieldNameRegex A regular expression to identify the field by name.
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> exclude(String fieldNameRegex);

    /**
     * Exclude fields searching by type. The excluded fields will not be changed.
     *
     * @param clazz The type to be ignored.
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> exclude(Class<?> clazz);

    /**
     * Mr Polite needs to know which types must be used in the generic fields. For instance:
     * <p>
     * <code>
     * Listener&lt;Foo&gt; listener = MrPolite.one(Listener.class).please();
     * </code>
     * </p>
     * Mr Polite doesn't know you want a type Foo in the generics, so you must specify it.
     * <p>
     * <code>
     * Listener&lt;Foo&gt; listener = MrPolite.one(Listener.class).withGenerics(Foo.class).please();
     * </code>
     * </p>
     *
     * @param clazz the class or classes to use for generics. The number of classes must be the same number of generics declared in the expected object.
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> withGenerics(Class<?>... clazz);

    /**
     * By default the values already initialized are also override with random values,
     * the default behavior can be changed with this method.
     *
     * @param override true to override also the values already initialized, false otherwise.
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> overrideValues(boolean override);

    /**
     * Override also the values in final fields. Take into account that final fields for
     * primitives and String are inlined in bytecode so it has no effect to change them.
     *
     * @return the same {@link PoliteDesire}
     */
    PoliteDesire<C, V> overrideFinals();
}
