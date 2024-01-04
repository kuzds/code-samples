package ru.kuzds.camunda.web2mq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.camunda.web2mq.dto.User;
import ru.kuzds.camunda.web2mq.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String start(@RequestBody User user) {
        return userService.start(user);
    }
}
