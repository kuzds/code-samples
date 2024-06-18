package ru.kuzds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.kuzds.dto.SomeDto;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static ru.kuzds.controller.CallbackController.HEADER_URL;
import static ru.kuzds.controller.CallbackController.TRANSACTION_NOT_FOUND;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
//@EnabledIf(expression = "#{environment['SPRING_PROFILES_ACTIVE'] == 'integration-test'}")
class CallbackControllerIntegrationTests {

    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());
    @Container
    private static final MockServerContainer CONTAINER = new MockServerContainer(MOCKSERVER_IMAGE);
    private static MockServerClient CLIENT;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.openfeign.client.config.mockserver-client.readTimeout", () -> 2000);
    }

    @BeforeAll
    public static void init() {
        CLIENT = new MockServerClient(CONTAINER.getHost(), CONTAINER.getServerPort());
    }

    @AfterAll
    public static void destroy() {
        CLIENT.close();
    }

    @Test
    void operation_200() throws Exception {
        SomeDto someDto = GENERATOR.nextObject(SomeDto.class);
        String responseBody = "OK";

        byte[] content = objectMapper.writeValueAsBytes(someDto);
        CLIENT.when(request()
                        .withMethod("POST")
                        .withPath("/some")
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withBody(content))
                .respond(response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withBody(responseBody));

        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_URL, CONTAINER.getEndpoint() + "/some")
                .bodyValue(content)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(responseBody);
    }

    @Test
    void operation_500() throws Exception {
        SomeDto someDto = GENERATOR.nextObject(SomeDto.class);
        String responseBody = "500 INTERNAL_SERVER_ERROR";

        byte[] content = objectMapper.writeValueAsBytes(someDto);
        CLIENT.when(request()
                        .withMethod("POST")
                        .withPath("/some")
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withBody(content))
                .respond(response()
                        .withStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withBody(responseBody));

        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_URL, CONTAINER.getEndpoint() + "/some")
                .bodyValue(content)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(TRANSACTION_NOT_FOUND);
    }

}
