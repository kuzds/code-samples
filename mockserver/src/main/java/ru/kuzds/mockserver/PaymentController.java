package ru.kuzds.mockserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
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
