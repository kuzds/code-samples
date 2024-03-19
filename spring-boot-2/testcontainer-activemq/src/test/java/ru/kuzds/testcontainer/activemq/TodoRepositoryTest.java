package ru.kuzds.testcontainer.activemq;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kuzds.testcontainer.activemq.dto.Todo;
import ru.kuzds.testcontainer.activemq.service.TodoService;

import static org.mockito.Mockito.only;
import static ru.kuzds.testcontainer.activemq.listener.TodoListener.DESTINATION;

@SpringBootTest
@Testcontainers
class TodoRepositoryTest {
    private static final String TCP_FORMAT = "tcp://%s:%d";
    private static final String ACTIVEMQ_IMAGE = "rmohr/activemq";
    private static final int ACTIVEMQ_PORT = 61616;
    private static final EasyRandom GENERATOR = new EasyRandom();

    @SpyBean
    TodoService todoService;

    @SuppressWarnings("rawtypes")
    @Container
    private static final GenericContainer activemq = new GenericContainer(ACTIVEMQ_IMAGE).withExposedPorts(ACTIVEMQ_PORT);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        final String url = String.format(TCP_FORMAT, activemq.getHost(), activemq.getFirstMappedPort());
        registry.add("kuzds.activemq.broker-url", () -> url);
        registry.add("kuzds.activemq.user", () -> "admin");
        registry.add("kuzds.activemq.password", () -> "admin");
    }

    @Autowired
    JmsTemplate todoJmsTemplate;

    @Test
    void shouldGetPendingTodos() {
        Todo todo = GENERATOR.nextObject(Todo.class);
        todoJmsTemplate.convertAndSend(DESTINATION, todo);
        Mockito.verify(todoService, only()).log(todo);
    }
}
