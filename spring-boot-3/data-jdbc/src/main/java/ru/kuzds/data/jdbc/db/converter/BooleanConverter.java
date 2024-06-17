package ru.kuzds.data.jdbc.db.converter;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanConverter {

    @WritingConverter
    public static class Writer implements Converter<Boolean, String> {

        @Override
        public String convert(@Nonnull Boolean source) {
            return Boolean.TRUE.equals(source) ? "T" : "F";
        }
    }

    @ReadingConverter
    public static class Reader implements Converter<String, Boolean> {
        @Override
        public Boolean convert(@Nonnull String source) {
            return source.equalsIgnoreCase("T") ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}
