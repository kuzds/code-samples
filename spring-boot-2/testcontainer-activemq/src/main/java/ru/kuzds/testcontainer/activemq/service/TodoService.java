package ru.kuzds.testcontainer.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuzds.testcontainer.activemq.dto.Todo;

@Slf4j
@Service
public class TodoService {

    public void log(Todo todo) {
        log.info(todo.toString());
    }
}
