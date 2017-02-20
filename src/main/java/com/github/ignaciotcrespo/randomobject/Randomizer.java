package com.github.ignaciotcrespo.randomobject;

import java.util.Random;

/**
 * Created by crespo on 2/20/17.
 */
public class Randomizer {

    private Random random = new Random();

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
