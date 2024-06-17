package ru.kuzds.reactor.rabbitmq.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.kuzds.reactor.rabbitmq.demo.dto.DemoRequest;
import ru.kuzds.reactor.rabbitmq.demo.dto.DemoResponse;
import ru.kuzds.reactor.rabbitmq.protocol.dto.ProtocolResponse;
import ru.kuzds.reactor.rabbitmq.protocol.helper.ProtocolHelper;

import java.time.Duration;

@Testcontainers
@SpringBootTest
class DemoSalServiceIntegrationTest {

    private final static EasyRandom GENERATOR = new EasyRandom();
    private final static String ROUTING_KEY = "demo-command";
    private final static String EXECUTOR_CONNECTION_NAME = "executor-demo-command";
    private final static String HANDLER_CONNECTION_NAME = "demo-command";

    @Autowired
    ProtocolHelper protocolHelper;

    @Autowired
    ObjectMapper objectMapper;

    static final DockerImageName RABBIT_IMAGE = DockerImageName
            .parse("harbor.online.tkbbank.ru/library/rabbitmq")
            .asCompatibleSubstituteFor("rabbitmq")
            .withTag("3.12-management");// Нужен с rabbitmqadmin

    @Container
    static final RabbitMQContainer RABBIT = new RabbitMQContainer(RABBIT_IMAGE);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("rmq.uri", () -> String.format("amqp://%s:%s@localhost:%s",
                RABBIT.getAdminUsername(), RABBIT.getAdminPassword(), RABBIT.getFirstMappedPort()));
        registry.add("rmq.connection-name", () -> HANDLER_CONNECTION_NAME);
        registry.add("protocol.routing-key.demo", () -> ROUTING_KEY);
    }

    @Test
    @SneakyThrows
    void test() {
        DemoRequest request = GENERATOR.nextObject(DemoRequest.class);
        byte[] bytes = objectMapper.writeValueAsBytes(request);

        Delivery delivery = protocolHelper.execCommand(EXECUTOR_CONNECTION_NAME, ROUTING_KEY, bytes, Duration.ofSeconds(10))
                .block();
        Assertions.assertThat(delivery).isNotNull();

        ProtocolResponse<DemoResponse> payResponse = objectMapper.readValue(delivery.getBody(), new TypeReference<>() {
        });

        DemoResponse expected = new DemoResponse(request.getMessage(), request.getValue());

        Assertions.assertThat(payResponse.getResult()).isEqualTo(expected);
    }
}