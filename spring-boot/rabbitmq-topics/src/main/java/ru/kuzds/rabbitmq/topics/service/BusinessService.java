package ru.kuzds.rabbitmq.topics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;
    private final static List<String> ROUTING_KEYS = Arrays.asList(
            "customer.created",
            "customer.edited",
            "customer.deleted",
            "order.created",
            "order.edited",
            "order.deleted",
            "invoice.created",
            "invoice.edited",
            "invoice.deleted"
    );

    private int messageNumber = 0;

    private final Random random = new Random();

    @Scheduled(fixedDelay = 500, initialDelay = 500)
    public void sendMessage() {
        String routingKey = randomRoutingKey();
        String message = String.format("Event no. %d of type '%s'", ++messageNumber, routingKey);
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
//        log.info("Published message '{}'", message);
    }

    private String randomRoutingKey() {
        return ROUTING_KEYS.get(random.nextInt(ROUTING_KEYS.size()));
    }

    @RabbitListener(queues = "#{customerQueue.name}")
    public void listen(String message) {
        log.info("Received message: {}", message);
    }

}
