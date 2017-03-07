package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

/**
 * Created by crespo on 2/24/17.
 */
abstract class RandomGenerator extends DataGenerator {

    final Randomizer mRandomizer;

    RandomGenerator(Randomizer randomizer) {
        mRandomizer = randomizer;
    }
}
