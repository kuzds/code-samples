package ru.kuzds.mockserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuzds.mockserver.dto.PaymentRequest;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kuzds.mockserver.controller.PaymentControllerAdvice.SERVICE_UNAVAILABLE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PaymentControllerUnavailableIntegrationTests {

    private static final EasyRandom GENERATOR = new EasyRandom();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("ru.kuzds.mockserver.url", () -> "http://localhost:12345");
    }

    @Test
    void test_unavailable() throws Exception {
        PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);

        byte[] content = objectMapper.writeValueAsBytes(paymentRequest);


        mvc.perform(post("/payment")
                        .header("mercId", UUID.randomUUID().toString())
                        .param("extId", UUID.randomUUID().toString())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(SERVICE_UNAVAILABLE));
    }
}
