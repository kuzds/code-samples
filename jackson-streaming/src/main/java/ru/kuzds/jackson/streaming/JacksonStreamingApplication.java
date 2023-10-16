package ru.kuzds.jackson.streaming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@SpringBootApplication
public class JacksonStreamingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JacksonStreamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<Contact> contacts;
		try (InputStream is = classLoader.getResourceAsStream("example.json")) {
			contacts = JsonHelper.parseJson(is, Contact.class);
		}

		log.info(contacts.toString());

		Path out = Paths.get("./out/test.json");
		Files.createDirectories(out.getParent());
		try (OutputStream os = new FileOutputStream(out.toFile())) {
			JsonHelper.generateJson(contacts, os);
		}
	}
}
