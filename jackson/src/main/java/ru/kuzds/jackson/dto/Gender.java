package ru.kuzds.jackson.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.RequiredArgsConstructor;
import ru.kuzds.jackson.desirealizer.GenderDeserializer;

@JsonDeserialize(using = GenderDeserializer.class)
@RequiredArgsConstructor
public enum Gender {
    UNDEFINED("Н/О"),
    MALE("М"),
    FEMALE("Ж");

    private final String alias;

    @JsonValue
    public String getAlias() {
        return alias;
    }
}
