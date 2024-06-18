package ru.kuzds.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Адаптер отправки колбэков ")
public class CallbackController {

    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String HEADER_URL = "Header-URL";
    private static final String HEADER_LOGIN = "Header-Login";
    private static final String HEADER_SIGN = "Header-Sign";

    private final WebClient.Builder webClientBuilder;

    @PostMapping
    @Operation(summary = "Переслать по URL, хранящемся в \"" + HEADER_URL + "\" заголовке")
    Mono<String> operation(@RequestHeader(value = HEADER_URL) String url,
                           @RequestHeader(value = HEADER_LOGIN, required = false) String login,
                           @RequestHeader(value = HEADER_SIGN, required = false) String sign,
                           @RequestBody String request) {

        log.debug("-------------------------------------------------------------------------------");
        log.debug("{}: {}, {}: {}, {}: {}", HEADER_URL, url, HEADER_LOGIN, login, HEADER_SIGN, sign);

        return webClientBuilder.baseUrl(url).build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_LOGIN, login)
                .header(HEADER_SIGN, sign)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    ResponseEntity<String> handleInternalServerError(WebClientResponseException.InternalServerError e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.ok().body(TRANSACTION_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR);
    }
}
