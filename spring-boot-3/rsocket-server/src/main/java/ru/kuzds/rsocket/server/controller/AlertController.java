package ru.kuzds.rsocket.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import ru.kuzds.rsocket.server.dto.Alert;

@Controller
@Slf4j
public class AlertController {
    @MessageMapping("alert")
    public Mono<Void> setAlert(Mono<Alert> alertMono) {
        return alertMono
                .doOnNext(alert ->
                        log.info("{} alert ordered by {} at {}",
                                alert.getLevel(),
                                alert.getOrderedBy(),
                                alert.getOrderedAt())
                )
                .thenEmpty(Mono.empty());
    }
}