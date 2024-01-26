package ru.kuzds.data.jdbc.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import ru.kuzds.data.jdbc.db.converter.BooleanConverter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class UserDatabaseConfig extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
//        return Arrays.asList(new ZonedDateTimeConverter.Writer(), new ZonedDateTimeConverter.Reader());
        return Arrays.asList(new BooleanConverter.Writer(), new BooleanConverter.Reader());
    }
}