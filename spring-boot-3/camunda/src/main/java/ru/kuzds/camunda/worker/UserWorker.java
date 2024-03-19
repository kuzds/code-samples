package ru.kuzds.camunda.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kuzds.camunda.dto.User;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserWorker {

    private final ZeebeClient zeebeClient;

    @JobWorker(type = "kuzds.logUser")
    public void logUser(@Variable User user, @Variable String roleAlias) {
        log.info("Received user: {} with role: {}", user, roleAlias);
    }

    @JobWorker(type = "kuzds.throw-event")
    public void mockSend(@Variable String correlationKey,
                         @Variable String messageName) {
        zeebeClient.newPublishMessageCommand()
                .messageName(messageName)
                .correlationKey(correlationKey)
                .send().join();
    }
}
