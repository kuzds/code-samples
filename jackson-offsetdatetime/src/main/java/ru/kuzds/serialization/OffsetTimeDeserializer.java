package ru.kuzds.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OffsetTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss[.[SSSSSSSSS][SSSSSSSS][SSSSSSS][SSSSSS][SSSSS][SSSS][SSS][SS][S]][ZZZZZ]")
                    .withZone(ZoneId.of("UTC"));

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        OffsetTime offsetTime = OffsetTime.parse(p.getText(), formatter);
        return offsetTime.atDate(LocalDate.EPOCH);
    }
}
