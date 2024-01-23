package ru.kuzds.rabbitmq.topics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqTopicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqTopicsApplication.class, args);
	}

}
