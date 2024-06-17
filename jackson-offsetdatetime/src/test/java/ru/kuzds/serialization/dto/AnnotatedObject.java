package ru.kuzds.serialization.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.serialization.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnotatedObject {
    @JsonSerialize(using = OffsetDateSerializer.class)
    @JsonDeserialize(using = OffsetDateDeserializer.class)
    OffsetDateTime date;

    @JsonSerialize(using = OffsetTimeSerializer.class)
    @JsonDeserialize(using = OffsetTimeDeserializer.class)
    OffsetDateTime time;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    OffsetDateTime dateTime;
}
