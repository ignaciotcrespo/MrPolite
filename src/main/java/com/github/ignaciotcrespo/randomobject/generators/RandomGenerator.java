package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

/**
 * Created by crespo on 2/24/17.
 */
abstract class RandomGenerator extends DataGenerator {

    Randomizer mRandomizer;

    RandomGenerator(int seed) {
        if (seed > 0) {
            mRandomizer = new Randomizer(seed);
        } else {
            mRandomizer = new Randomizer();
        }
    }
}
