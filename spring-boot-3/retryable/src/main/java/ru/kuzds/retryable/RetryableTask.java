package ru.kuzds.retryable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryableTask {
    @Retryable(retryFor = {IllegalArgumentException.class}, maxAttempts = 5)
    public void task() throws IllegalArgumentException {
        log.info("task");
        throw new IllegalArgumentException("Something went wrong");
    }

    @Recover
    public void recover(IllegalArgumentException e) {
        log.info("recover");
    }
}
