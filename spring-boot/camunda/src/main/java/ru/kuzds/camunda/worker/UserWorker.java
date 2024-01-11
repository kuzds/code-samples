package ru.kuzds.camunda.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.PublishMessageCommandStep1;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kuzds.camunda.dto.User;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserWorker {

    private final ZeebeClient zeebeClient;

    @JobWorker(type = "kuzds.logUser")
    public void logUser(@Variable User user) {
        log.info("Received user: " + user);
    }

    @JobWorker(type = "kuzds.mockSend")
    public void mockSend(@Variable User user) {
        PublishMessageCommandStep1.PublishMessageCommandStep3 step3 = zeebeClient.newPublishMessageCommand()
                .messageName("kuzds.EVENT.mock")
                .correlationKey(user.getId());

        step3 = step3.variables(Map.of());

        step3.send().join();
    }
}
