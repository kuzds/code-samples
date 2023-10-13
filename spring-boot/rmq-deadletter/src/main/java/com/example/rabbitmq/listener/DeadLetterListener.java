package com.example.rabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterListener {

    @RabbitListener(queues = "#{destinationQueue.name}")
    public void listen(String message) {
        log.info("Received dead letter: {}", message);
    }
}
