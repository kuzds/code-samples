package ru.kuzds.data.jdbc.db.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

// НЕ РАБОТАЕТ
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZonedDateTimeConverter {

    @Component
    @ReadingConverter
    public static class Reader implements Converter<Timestamp, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(Timestamp timestamp) {
            LocalDateTime withoutTimezone = timestamp.toLocalDateTime();
            return withoutTimezone.atZone(ZoneId.systemDefault());

        }
    }

    @Component
    @WritingConverter
    public static class Writer implements Converter<ZonedDateTime, Timestamp> {

        @Override
        public Timestamp convert(ZonedDateTime zonedDateTime) {
            LocalDateTime withoutTimezone = zonedDateTime.toLocalDateTime();
            return Timestamp.valueOf(withoutTimezone);
        }
    }

}
