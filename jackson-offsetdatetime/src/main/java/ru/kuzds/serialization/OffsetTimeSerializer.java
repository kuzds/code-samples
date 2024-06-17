package ru.kuzds.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OffsetTimeSerializer extends JsonSerializer<OffsetDateTime> {
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSZZZZZ")
                    .withZone(ZoneId.of("UTC"));


    @Override
    public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(offsetDateTime.format(formatter));
    }
}
