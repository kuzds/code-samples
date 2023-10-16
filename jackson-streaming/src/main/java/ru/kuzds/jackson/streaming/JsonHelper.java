package ru.kuzds.jackson.streaming;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonHelper {

    public static <T> List<T> parseJson(InputStream is, Class<T> clazz) throws IOException {

        // Create and configure an ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        List<T> list = new ArrayList<>();
        // Create a JsonParser instance
        try (JsonParser jsonParser = mapper.getFactory().createParser(is)) {

            // Check the first token
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected content to be an array");
            }

            // Iterate over the tokens until the end of the array
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

                // Read a contact instance using ObjectMapper and do something with it
                T contact = mapper.readValue(jsonParser, clazz);
                list.add(contact);
            }
        }
        return list;
    }

    public static <T> void generateJson(List<T> elements, OutputStream os) throws IOException {

        // Create and configure an ObjectMapper instance
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Create a JsonGenerator instance
        try (JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(os)) {

            // Write the start array token
            jsonGenerator.writeStartArray();

            // Iterate over the contacts and write each contact as a JSON object
            for (T element : elements) {

                // Write a contact instance as JSON using ObjectMapper
                mapper.writeValue(jsonGenerator, element);
            }

            // Write the end array token
            jsonGenerator.writeEndArray();
        }
    }
}
