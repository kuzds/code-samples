package ru.kuzds.rabbitmq.topics.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    private final static String EXCHANGE_NAME = "kuzds.test.exchange";
    private final static String QUEUE_NAME = "kuzds.test.queue";
    private final static String TOPIC = "customer.*";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "kuzds.rabbitmq")
    RabbitProperties rabbitProperties() {
        return new RabbitProperties();
    }

    @Bean
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        factory.setVirtualHost(rabbitProperties.getVirtualHost());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue customerQueue() {
        return new Queue(QUEUE_NAME, false, false, true);
    }

    @Bean
    public Binding binding(Queue customerQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(customerQueue)
                .to(topicExchange)
                .with(TOPIC);
    }
}
