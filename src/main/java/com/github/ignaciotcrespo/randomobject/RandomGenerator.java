package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/24/17.
 */
public abstract class RandomGenerator extends DataGenerator {

    final int seed;
    Randomizer mRandomizer;

    public RandomGenerator(int seed) {
        this.seed = seed;
        if(seed > 0) {
            mRandomizer = new Randomizer(seed);
        } else {
            mRandomizer = new Randomizer();
        }
    }
}
