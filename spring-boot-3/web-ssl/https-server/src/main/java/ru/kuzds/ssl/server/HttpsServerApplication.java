package ru.kuzds.ssl.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@SpringBootApplication
public class HttpsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpsServerApplication.class, args);
	}

	@GetMapping("/test")
	public String test() {
		return "OK";
	}
}