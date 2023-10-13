package com.example.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitProperties {
    private String host;
    private Integer port;
    private String virtualHost;
    private String username;
    private String password;
}
