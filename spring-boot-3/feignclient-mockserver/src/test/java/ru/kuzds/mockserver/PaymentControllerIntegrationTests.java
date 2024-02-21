package ru.kuzds.mockserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Delay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.kuzds.mockserver.dto.PaymentRequest;
import ru.kuzds.mockserver.dto.PaymentResponse;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kuzds.mockserver.controller.PaymentController.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'integration-test'}")
class PaymentControllerIntegrationTests {

    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());
    @Container
    private static final MockServerContainer CONTAINER = new MockServerContainer(MOCKSERVER_IMAGE);
    private static MockServerClient CLIENT;

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("ru.kuzds.mockserver.url", CONTAINER::getEndpoint);
        registry.add("ru.kuzds.unavailable-service.url", () -> "http://localhost:1234");
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
    void test_200() throws Exception {
        PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);
        PaymentResponse responseBody = GENERATOR.nextObject(PaymentResponse.class);

        byte[] content = objectMapper.writeValueAsBytes(paymentRequest);
        CLIENT.when(request()
                        .withMethod("POST")
                        .withPath("/payment")
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withBody(content))
                .respond(response()
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withStatusCode(HttpStatus.OK.value())
                        .withBody(objectMapper.writeValueAsBytes(responseBody)));

        MvcResult result = mvc.perform(post("/payment")
                        .header("mercId", UUID.randomUUID().toString())
                        .param("extId", UUID.randomUUID().toString())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        MockHttpServletResponse httpServletResponse = result.getResponse();
        PaymentResponse response = objectMapper.readValue(httpServletResponse.getContentAsByteArray(), PaymentResponse.class);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(responseBody);
    }

    @Test
    void test_404() throws Exception {
        PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);
        byte[] content = objectMapper.writeValueAsBytes(paymentRequest);
        CLIENT.when(request()
                        .withMethod("POST")
                        .withPath("/payment")
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withBody(content))
                .respond(response()
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withStatusCode(HttpStatus.NOT_FOUND.value()));

        mvc.perform(post("/payment")
                        .header("mercId", UUID.randomUUID().toString())
                        .param("extId", UUID.randomUUID().toString())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(NOT_FOUND));
    }

    @Test
    void test_readTimeout() throws Exception {
        PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);
        byte[] content = objectMapper.writeValueAsBytes(paymentRequest);
        CLIENT.when(request()
                        .withMethod("POST")
                        .withPath("/payment")
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withBody(content))
                .respond(response()
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                        .withStatusCode(HttpStatus.GATEWAY_TIMEOUT.value())
                        .withDelay(Delay.seconds(60)));

        mvc.perform(post("/payment")
                        .header("mercId", UUID.randomUUID().toString())
                        .param("extId", UUID.randomUUID().toString())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(READ_TIMEOUT));
    }

    @Test
    void test_unavailable() throws Exception {
        mvc.perform(get("/unavailable"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(SERVICE_UNAVAILABLE));
    }
}
