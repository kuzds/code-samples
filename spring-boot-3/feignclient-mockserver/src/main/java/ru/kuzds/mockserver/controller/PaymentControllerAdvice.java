package ru.kuzds.mockserver.controller;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Slf4j
@RestControllerAdvice
public class PaymentControllerAdvice {

    public static final String NOT_FOUND = "Server returned 404";
    public static final String READ_TIMEOUT = "Read timed out";
    public static final String SERVICE_UNAVAILABLE = "Service unavailable";
    public static final String INTERNAL_ERROR = "Internal server error";

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignExceptionNotFound(FeignException.NotFound e) {
        return ResponseEntity.internalServerError().body(NOT_FOUND);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<String> handleRetryableException(RetryableException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        if (rootCause instanceof ConnectException connectException) {
            return handleConnectException(connectException);
        } else if (rootCause instanceof SocketTimeoutException socketTimeoutException) {
            return handleSocketTimeoutException(socketTimeoutException);
        } else {
            return handleException(e);
        }
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleConnectException(ConnectException e) {
        return ResponseEntity.internalServerError().body(SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> handleSocketTimeoutException(SocketTimeoutException e) {
        return ResponseEntity.internalServerError().body(READ_TIMEOUT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(INTERNAL_ERROR);
    }
}
