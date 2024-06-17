package ru.kuzds.data.jdbc.db.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.sql.Date;
import java.time.*;

@ReadingConverter
public class SqlDateToOffsetDateTimeReadingConverter implements Converter<Date, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(Date source) {
        LocalDate localDate = Instant.ofEpochMilli(source.getTime()).atZone(ZoneId.of("GMT+3")).toLocalDate();
        return OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    }
}
