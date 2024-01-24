package ru.kuzds.easy.random;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.util.UUID;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class EasyRandomHelper {

    public static Payment getPaymentRequest() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named(Payment.Fields.requestId).and(ofType(UUID.class)))
                .randomize(Long.class, new LongRangeRandomizer((long) Integer.MIN_VALUE, (long) Integer.MAX_VALUE));
        return new EasyRandom(parameters).nextObject(Payment.class);
    }
}
