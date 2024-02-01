package ru.kuzds.retryable;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequiredArgsConstructor
@EnableRetry
public class RetryableApplication implements CommandLineRunner {

	private final RetryableTask retryableTask;

	public static void main(String[] args) {
		SpringApplication.run(RetryableApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		retryableTask.task();
	}
}