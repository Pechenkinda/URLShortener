package com.dmitriy.shortener.utils;

import lombok.experimental.UtilityClass;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.springframework.data.annotation.Id;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class EasyRandomTestUtils {

    public EasyRandom create() {
        var parameters = new EasyRandomParameters()
                .objectPoolSize(100)
                .randomizationDepth(5)
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(2, 4)
                .randomize(Integer.class, new IntegerRangeRandomizer(0, Integer.MAX_VALUE))
                .randomize(Long.class, new LongRangeRandomizer(0L, Long.MAX_VALUE))
                .excludeField(f -> f.getAnnotation(Id.class) != null) // use database ids
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(true)
                .ignoreRandomizationErrors(true);
        return new EasyRandom(parameters);
    }
}
