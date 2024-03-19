package ru.kuzds.mockserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kuzds.mockserver.client.MockserverClient;
import ru.kuzds.mockserver.dto.PaymentRequest;
import ru.kuzds.mockserver.dto.PaymentResponse;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final MockserverClient mockserverClient;

    @PostMapping("/payment")
    public PaymentResponse payment(@RequestHeader("mercId") String mercId,
                                   @RequestParam("extId") String extId,
                                   @RequestBody PaymentRequest request) {
        return mockserverClient.payment(mercId, extId, request);
    }
}
