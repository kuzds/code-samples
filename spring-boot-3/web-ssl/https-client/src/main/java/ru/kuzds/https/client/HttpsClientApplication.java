package ru.kuzds.https.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class HttpsClientApplication implements CommandLineRunner {

    private final WebClient webClient;

    public static void main(String[] args) {
        SpringApplication.run(HttpsClientApplication.class, args);
    }

    @Value("${demo.path.test}")
	private String pathTest;

    @Override
    public void run(String... args) throws Exception {
        String response = webClient.get()
                .uri(pathTest)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Response: " + response);
    }


    // может быть выполнен несколько раз
//    @EventListener(ContextRefreshedEvent.class)
//    public void onApplicationEvent() {
//        String response = webClient.get()
//                .uri(pathTest)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        log.info("Response: " + response);
//    }
}
