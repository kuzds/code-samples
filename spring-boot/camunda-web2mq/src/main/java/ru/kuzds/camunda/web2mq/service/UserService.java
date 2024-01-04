package ru.kuzds.camunda.web2mq.service;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuzds.camunda.web2mq.dto.User;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ZeebeClient zeebeClient;

    public String start(@NonNull User user) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", user);

        zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("Web2mqProcess").latestVersion()
            .variables(variables)
            .send()
            .join();

        return "Started";
    }
}
