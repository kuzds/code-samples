package ru.kuzds.mockserver.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class RequestLoggerClientConfig {

    @Bean
    public CustomFeignLogger customFeignLogger() {
        return new CustomFeignLogger();
    }

}
