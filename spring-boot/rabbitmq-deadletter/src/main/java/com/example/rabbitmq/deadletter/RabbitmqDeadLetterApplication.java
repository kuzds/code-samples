package com.example.rabbitmq.deadletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDeadLetterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDeadLetterApplication.class, args);
    }
}
