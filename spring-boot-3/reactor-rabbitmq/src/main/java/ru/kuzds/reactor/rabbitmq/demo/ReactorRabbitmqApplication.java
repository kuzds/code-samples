package ru.kuzds.reactor.rabbitmq.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactorRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactorRabbitmqApplication.class, args);
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
