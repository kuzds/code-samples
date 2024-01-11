package ru.kuzds.camunda.service;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuzds.camunda.dto.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ZeebeClient zeebeClient;

    public String start() {

        User user = User.builder().id(UUID.randomUUID().toString()).email("kuzds@bk.ru").build();

        Map<String, Object> variables = new HashMap<>();
        variables.put("user", user);

        zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("camunda_kuzds").latestVersion()
            .variables(variables)
            .send()
            .join();

        return String.format("Process started. User with id %s", user.getId());
    }
}
