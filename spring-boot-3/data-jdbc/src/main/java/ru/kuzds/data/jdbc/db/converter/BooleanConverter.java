package ru.kuzds.data.jdbc.db.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class BooleanConverter {

    @WritingConverter
    public static class Writer implements Converter<Boolean, String> {

        @Override
        public String convert(Boolean source) {
            return source != null && source ? "T" : "F";
        }
    }

    @ReadingConverter
    public static class Reader implements Converter<String, Boolean> {

        @Override
        public Boolean convert(String source) {
            return source != null && source.equalsIgnoreCase("T") ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}
