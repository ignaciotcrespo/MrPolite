package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

/**
 * Created by crespo on 2/28/17.
 */
public class Generators {

    public static DataGenerator[] createDefault(Randomizer randomizer) {
        return new DataGenerator[]{
                new EnumDataGenerator(randomizer),
                new BooleanDataGenerator(randomizer),
                new ByteDataGenerator(randomizer),
                new ShortDataGenerator(randomizer),
                new CharDataGenerator(randomizer),
                new IntegerDataGenerator(randomizer),
                new LongDataGenerator(randomizer),
                new FloatDataGenerator(randomizer),
                new DoubleDataGenerator(randomizer),
                new StringDataGenerator(randomizer),
                new DateDataGenerator(randomizer),
                new CalendarDataGenerator(randomizer)
        };
    }

}
