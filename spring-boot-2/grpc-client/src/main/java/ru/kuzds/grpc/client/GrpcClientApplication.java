package ru.kuzds.grpc.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.piomin.services.grpc.account.model.AccountProto;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class GrpcClientApplication implements CommandLineRunner {

	private final AccountClient accountClient;

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		AccountProto.Accounts accounts = accountClient.getAccountsByCustomerId(1);
		log.info(accounts.toString());
	}
}
