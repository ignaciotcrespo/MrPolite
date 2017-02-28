package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

/**
 * Created by crespo on 2/24/17.
 */
abstract class RandomGenerator extends DataGenerator {

    final Randomizer mRandomizer;

    RandomGenerator(Randomizer randomizer) {
        mRandomizer = randomizer;
    }
}
