package ru.kuzds.validation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.kuzds.validation.dto.PayRequest;
import ru.kuzds.validation.service.PayService;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = PayController.class) // Не загружает весь контекст. Нужен mock/spy PayService
class PayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean // @MockBean некорректно отрабатывает с аннотацией @Validated
    private PayService service;

    @Test
    void testValidation() throws Exception {

        PayRequest payRequest = getPayRequestBuilder()
                .build();

        testRequest(payRequest, status().isOk());

        payRequest = getPayRequestBuilder()
                .paymentUUID("bad")
                .build();

        testRequest(payRequest, status().isBadRequest());

        payRequest = getPayRequestBuilder()
                .pan("")
                .build();

        testRequest(payRequest, status().isBadRequest());

        payRequest = getPayRequestBuilder()
                .exp("bad")
                .build();

        testRequest(payRequest, status().isBadRequest());

        payRequest = getPayRequestBuilder()
                .method("bad")
                .build();

        testRequest(payRequest, status().isBadRequest());

        payRequest = getPayRequestBuilder()
                .initTime("2023/10/13 10:54:00")
                .build();

        testRequest(payRequest, status().isBadRequest());
    }

    @Test
    void testServiceCall() throws Exception {
        PayRequest payRequest = getPayRequestBuilder()
                .build();

        testRequest(payRequest, status().isOk());

        ArgumentCaptor<PayRequest> userCaptor = ArgumentCaptor.forClass(PayRequest.class);
        verify(service, times(1)).pay(userCaptor.capture());

        Assertions.assertEquals(userCaptor.getValue(), payRequest);
    }

    private PayRequest.PayRequestBuilder getPayRequestBuilder() {
        return PayRequest.builder()
                .paymentUUID(UUID.randomUUID().toString())
                .pan("pan")
                .exp("1100")
                .method("bank_card")
                .initTime("2023-10-13 10:54:00");
    }

    private void testRequest(PayRequest payRequest, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(post("/pay")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(payRequest)))
                .andExpect(resultMatcher);
    }
}