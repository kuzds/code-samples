package ru.kuzds.mockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MockserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockserverApplication.class, args);
	}

}
