package com.example.rabbitmq.deadletter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rabbitmq.deadletter.config.RabbitConfig.PARKING_QUEUE_NAME;
import static com.example.rabbitmq.deadletter.config.RabbitConfig.X_MESSAGE_TTL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("dead-letter")
public class DeadLetterController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping
    public String deadLetter() {
        String value = "This is a sample message";
        String response = String.format("Message was sent ... (wait for %dms)!", X_MESSAGE_TTL);

        rabbitTemplate.convertAndSend("", PARKING_QUEUE_NAME, value);
        log.info(response);

        return response;
    }

    @GetMapping("/{expiration}")
    public String deadLetter(@PathVariable("expiration") String expiration) {

        try {
            int expirationInteger = Integer.parseInt(expiration);
            if (expirationInteger > X_MESSAGE_TTL) {
                return "Expiration greater than X_MESSAGE_TTL. Remove X_MESSAGE_TTL if necessary";
            }
        } catch (NumberFormatException e) {
            return "Expiration should be numeric";
        }

        String value = String.format("This is a sample message with expiration: %s", expiration);
        String response = String.format("Message was sent ... (wait for %sms)!", expiration);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(expiration);
        Message message = new Message(value.getBytes(), messageProperties);

        rabbitTemplate.convertAndSend("", PARKING_QUEUE_NAME, message);
        log.info(response);

        return response;
    }
}
