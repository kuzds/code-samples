package ru.kuzds.jackson.desirealizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.kuzds.jackson.dto.Gender;

import java.io.IOException;
import java.util.Arrays;

import static ru.kuzds.jackson.dto.Gender.UNDEFINED;

public class GenderDeserializer extends JsonDeserializer<Gender> {
    @Override
    public Gender getNullValue(DeserializationContext context) {
        return UNDEFINED;
    }

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext context) {
        String stringValue;
        try {
            stringValue = p.getValueAsString();
        } catch (IOException e) {
            return UNDEFINED;
        }

        return Arrays.stream(Gender.values())
                .filter(e -> e.getAlias().equalsIgnoreCase(stringValue)).findAny().orElse(UNDEFINED);
    }
}