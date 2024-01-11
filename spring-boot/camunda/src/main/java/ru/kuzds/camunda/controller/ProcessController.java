package ru.kuzds.camunda.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.camunda.service.ProcessService;

@RequiredArgsConstructor
@RestController
public class ProcessController {

    private final ProcessService processService;

    @GetMapping("/start")
    public String start() {
        return processService.start();
    }
}
