package ru.kuzds.mockserver;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    public static final String NOT_FOUND = "Server returned 404";
    public static final String READ_TIMEOUT = "Read timed out";
    public static final String SERVICE_UNAVAILABLE = "Service unavailable";

    private final MockserverClient mockserverClient;
    private final UnavailableServiceClient unavailableServiceClient;

    @PostMapping("/payment")
    public PaymentResponse payment(@RequestHeader("mercId") String mercId,
                                   @RequestParam("extId") String extId,
                                   @RequestBody PaymentRequest request) {
        return mockserverClient.payment(mercId, extId, request);
    }

    @GetMapping("/unavailable")
    public String unavailable() {
        return unavailableServiceClient.unavailable();
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignExceptionNotFound(FeignException.NotFound e) {
        return ResponseEntity.internalServerError().body(NOT_FOUND);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> handleSocketTimeoutException(SocketTimeoutException e) {
        return ResponseEntity.internalServerError().body(READ_TIMEOUT);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleConnectException(ConnectException e) {
        return ResponseEntity.internalServerError().body(SERVICE_UNAVAILABLE);
    }
}
