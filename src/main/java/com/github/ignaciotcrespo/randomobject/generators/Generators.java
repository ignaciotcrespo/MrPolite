package com.github.ignaciotcrespo.randomobject.generators;

/**
 * Created by crespo on 2/28/17.
 */
public class Generators {

    public static DataGenerator[] createDefault(int seed) {
        return new DataGenerator[]{
                new EnumDataGenerator(seed),
                new BooleanDataGenerator(seed),
                new ByteDataGenerator(seed),
                new ShortDataGenerator(seed),
                new CharDataGenerator(seed),
                new IntegerDataGenerator(seed),
                new LongDataGenerator(seed),
                new FloatDataGenerator(seed),
                new DoubleDataGenerator(seed),
                new StringDataGenerator(seed),
                new DateDataGenerator(seed),
                new CalendarDataGenerator(seed)
        };
    }

}
