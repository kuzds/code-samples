package ru.kuzds.camunda.testcontainer;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingWorker {

    @JobWorker(type = "kuzds.logging")
    public Map<String, Object> logging(@Variable String invariable) {
        log.info(">>>>>> Doing some job...");
        return Map.of("outvariable", invariable + UUID.randomUUID());
    }
}
