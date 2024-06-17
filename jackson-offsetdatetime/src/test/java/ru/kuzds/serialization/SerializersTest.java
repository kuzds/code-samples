package ru.kuzds.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import ru.kuzds.serialization.dto.AnnotatedObject;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.within;

class SerializersTest {

    private static final ObjectMapper OM;
    static {
        OM = JsonMapper.builder()
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    @SneakyThrows
    void circle() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(
                LocalDateTime.of(2024, Month.JUNE, 5, 0, 0, 0),
                ZoneOffset.of("+03:00")
        );

        assertCircle(offsetDateTime);
        assertCircle(OffsetDateTime.now());
    }

    @Test
    @SneakyThrows
    void serialization() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(
                LocalDateTime.of(2024, Month.APRIL, 24, 14, 23, 27, 90000000),
                ZoneOffset.UTC
        );

        AnnotatedObject annotatedObject = new AnnotatedObject();
        annotatedObject.setDateTime(offsetDateTime);
        annotatedObject.setDate(offsetDateTime);
        annotatedObject.setTime(offsetDateTime);

        String expected = "{\"date\":\"2024-04-24\",\"time\":\"14:23:27.090Z\",\"dateTime\":\"2024-04-24T14:23:27.090Z\"}";
        String actual = OM.writeValueAsString(annotatedObject);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    void deserialization() {
        assertTimeDeserialization("14:23:27Z");
        assertTimeDeserialization("14:23:27.0Z");
        assertTimeDeserialization("14:23:27.1Z");
        assertTimeDeserialization("14:23:27.09Z");
        assertTimeDeserialization("14:23:27.097Z");
        assertTimeDeserialization("14:23:27.0972Z");
        assertTimeDeserialization("14:23:27.09724Z");
        assertTimeDeserialization("14:23:27.097241Z");
        assertTimeDeserialization("14:23:27.0972412Z");
        assertTimeDeserialization("14:23:27.09724129Z");
        assertTimeDeserialization("14:23:27.097241293Z");
        assertTimeDeserialization("16:23:27.097+02:00");

        assertDateTimeDeserialization("2024-04-24T14:23:27Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.0Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.1Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.09Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.097Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.0972Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.09724Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.097241Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.0972412Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.09724129Z");
        assertDateTimeDeserialization("2024-04-24T14:23:27.097241293Z");
        assertDateTimeDeserialization("2024-04-24T16:23:27.097+02:00");

        assertDateDeserialization("2024-04-24");
    }

    private void assertCircle(OffsetDateTime offsetDateTime) throws JsonProcessingException {
        OffsetDateTime utcOffsetDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);

        AnnotatedObject expected = AnnotatedObject.builder()
                .date(utcOffsetDateTime)
                .time(utcOffsetDateTime)
                .dateTime(utcOffsetDateTime)
                .build();

        String stringDateTime = OM.writeValueAsString(expected);

        AnnotatedObject actual = OM.readValue(stringDateTime, AnnotatedObject.class);
        Assertions.assertThat(actual.getTime().getHour())
                .isEqualTo(expected.getTime().getHour());
        Assertions.assertThat(actual.getTime().getMinute())
                .isEqualTo(expected.getTime().getMinute());
        Assertions.assertThat(actual.getTime().getSecond())
                .isEqualTo(expected.getTime().getSecond());
        Assertions.assertThat(actual.getTime().getNano())
                .isCloseTo(expected.getTime().getNano(), Offset.offset(1000000)); // до 3-его знака

        Assertions.assertThat(actual.getDate().getYear())
                .isEqualTo(expected.getDate().getYear());
        Assertions.assertThat(actual.getDate().getMonthValue())
                .isEqualTo(expected.getDate().getMonthValue());
        Assertions.assertThat(actual.getDate().getDayOfMonth())
                .isEqualTo(expected.getDate().getDayOfMonth());

        Assertions.assertThat(actual.getDateTime())
                .isCloseTo(expected.getDateTime(), within(1, ChronoUnit.MILLIS));
    }

    private void assertDateTimeDeserialization(String string) throws JsonProcessingException {
        AnnotatedObject annotatedObject = OM.readValue(String.format("{\"dateTime\":\"%s\"}", string), AnnotatedObject.class);
        Assertions.assertThat(annotatedObject).isNotNull();

        OffsetDateTime offsetDateTime = annotatedObject.getDateTime();
        Assertions.assertThat(offsetDateTime).isNotNull();

        ZonedDateTime zoned = offsetDateTime.atZoneSameInstant(ZoneId.of("GMT+3"));

        Assertions.assertThat(zoned.getYear()).isEqualTo(2024);
        Assertions.assertThat(zoned.getMonthValue()).isEqualTo(4);
        Assertions.assertThat(zoned.getDayOfMonth()).isEqualTo(24);
        Assertions.assertThat(zoned.getHour()).isEqualTo(17);
        Assertions.assertThat(zoned.getMinute()).isEqualTo(23);
        Assertions.assertThat(zoned.getSecond()).isEqualTo(27);
    }

    private void assertTimeDeserialization(String string) throws JsonProcessingException {
        AnnotatedObject annotatedObject = OM.readValue(String.format("{\"time\":\"%s\"}", string), AnnotatedObject.class);
        Assertions.assertThat(annotatedObject).isNotNull();

        OffsetDateTime offsetTime = annotatedObject.getTime();
        Assertions.assertThat(offsetTime).isNotNull();

        ZonedDateTime zoned = offsetTime.atZoneSameInstant(ZoneId.of("GMT+3"));

        Assertions.assertThat(zoned.getHour()).isEqualTo(17);
        Assertions.assertThat(zoned.getMinute()).isEqualTo(23);
        Assertions.assertThat(zoned.getSecond()).isEqualTo(27);
    }

    private void assertDateDeserialization(String string) throws JsonProcessingException {
        AnnotatedObject annotatedObject = OM.readValue(String.format("{\"date\":\"%s\"}", string), AnnotatedObject.class);
        Assertions.assertThat(annotatedObject).isNotNull();

        OffsetDateTime offsetDate = annotatedObject.getDate();
        Assertions.assertThat(offsetDate).isNotNull();

        ZonedDateTime zoned = offsetDate.atZoneSameInstant(ZoneId.of("GMT+3"));

        Assertions.assertThat(zoned.getYear()).isEqualTo(2024);
        Assertions.assertThat(zoned.getMonthValue()).isEqualTo(4);
        Assertions.assertThat(zoned.getDayOfMonth()).isEqualTo(24);
    }
}