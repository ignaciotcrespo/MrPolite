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
package com.github.ignaciotcrespo.mrpolite.utils;

import java.util.Random;

/**
 * Created by crespo on 2/20/17.
 */
public class Randomizer {

    private final Random random;
    private final long seed;

    public Randomizer() {
        random = new Random();
        Object seedInternal = ClassUtils.getFieldValue(random, "seed");
        if (seedInternal instanceof Number) {
            seed = ((Number) seedInternal).longValue();
        } else {
            seed = (int) System.currentTimeMillis();
        }
    }

    public Randomizer(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    public int nextInt(int length) {
        return random.nextInt(length);
    }

    public int nextInt() {
        return random.nextInt();
    }

    public long nextLong() {
        return random.nextLong();
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public float nextFloat() {
        return random.nextFloat();
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public long getSeed() {
        return seed;
    }
}
