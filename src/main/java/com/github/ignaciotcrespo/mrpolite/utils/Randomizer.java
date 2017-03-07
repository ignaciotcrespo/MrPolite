package com.github.ignaciotcrespo.mrpolite.utils;

import java.util.Random;

/**
 * Created by crespo on 2/20/17.
 */
public class Randomizer {

    private final Random random;

    public Randomizer() {
        random = new Random();
    }

    public Randomizer(int seed) {
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
}
