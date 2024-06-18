package ru.kuzds.rest.sslcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestSslCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSslCheckApplication.class, args);
	}

}
