package com.github.ignaciotcrespo.randomobject;

import java.util.Random;

/**
 * Created by crespo on 2/20/17.
 */
class Randomizer {

    private final Random random;

    Randomizer() {
        random = new Random();
    }

    Randomizer(int seed) {
        random = new Random(seed);
    }

    int nextInt(int length) {
        return random.nextInt(length);
    }

    int nextInt() {
        return random.nextInt();
    }

    long nextLong() {
        return random.nextLong();
    }

    double nextDouble() {
        return random.nextDouble();
    }

    float nextFloat() {
        return random.nextFloat();
    }

    boolean nextBoolean() {
        return random.nextBoolean();
    }
}
