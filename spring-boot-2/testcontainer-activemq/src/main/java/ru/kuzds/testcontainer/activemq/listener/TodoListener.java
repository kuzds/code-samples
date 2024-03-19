package ru.kuzds.testcontainer.activemq.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.kuzds.testcontainer.activemq.dto.Todo;
import ru.kuzds.testcontainer.activemq.service.TodoService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodoListener {

    public static final String DESTINATION = "kuzds.todos";

    private final TodoService todoService;

    @JmsListener(destination = DESTINATION, containerFactory = "todoListenerContainerFactory")
    private void downloadReportAndSaveToCft(Todo todo) {
        todoService.log(todo);
    }
}
