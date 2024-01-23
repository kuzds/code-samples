package com.example.rabbitmq.deadletter.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public final static Integer X_MESSAGE_TTL = 5000;
    public final static String PARKING_QUEUE_NAME = "test.parking.queue";
    public final static String DESTINATION_QUEUE_NAME = "test.destination.queue";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "kuzds.rabbitmq")
    RabbitProperties rabbitProperties() {
        return new org.springframework.boot.autoconfigure.amqp.RabbitProperties();
    }

    @Bean
    public CachingConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        factory.setVirtualHost(rabbitProperties.getVirtualHost());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue parkingQueue() {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", X_MESSAGE_TTL);
        arguments.put("x-dead-letter-exchange", "");
        arguments.put("x-dead-letter-routing-key", DESTINATION_QUEUE_NAME);

        // For some reason continue live after process finished
        return new Queue(PARKING_QUEUE_NAME, false, false, true, arguments);
    }

    @Bean
    public Queue destinationQueue() {
        return new Queue(DESTINATION_QUEUE_NAME, false, false, true);
    }
}
