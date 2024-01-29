package ru.kuzds.mockserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PaymentControllerTests {

    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());
    @Container
    private static final MockServerContainer CONTAINER = new MockServerContainer(MOCKSERVER_IMAGE);

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("ru.kuzds.mockserver-client.url", CONTAINER::getEndpoint);
    }

    @Test
    void shouldSuccessCheck() throws Exception {
        PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);
        PaymentResponse responseBody = GENERATOR.nextObject(PaymentResponse.class);
        try (MockServerClient client = new MockServerClient(CONTAINER.getHost(), CONTAINER.getServerPort())) {
            byte[] content = objectMapper.writeValueAsBytes(paymentRequest);
            client.when(request()
                            .withMethod("POST")
                            .withPath("/payment")
//                            .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                            .withBody(content))
                    .respond(response()
                            .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                            .withBody(objectMapper.writeValueAsBytes(responseBody)));

            MvcResult result = mvc.perform(post("/api/v1/payment")
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
    }
}
