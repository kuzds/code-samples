package ru.kuzds.rsocket.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Controller
@Slf4j
class GreetingsController {

    @MessageMapping("greetings.{lang}")
    String greet(@DestinationVariable("lang") Locale lang, @Payload String name) {
        log.info("locale: " + lang.getLanguage());
        return "Hello, " + name + "!";
    }

    @MessageMapping("greetings/{name}")
    public Mono<String> handleGreeting(@DestinationVariable("name") String name, Mono<String> greetingMono) {
        return greetingMono
                .doOnNext(greeting -> log.info("Received a greeting from {} : {}", name, greeting))
                .map(greeting -> "Hello to you, too, " + name);
    }
}