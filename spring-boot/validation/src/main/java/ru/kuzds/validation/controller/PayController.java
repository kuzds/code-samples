package ru.kuzds.validation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzds.validation.dto.PayRequest;
import ru.kuzds.validation.service.PayService;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "PAY controller")
public class PayController {

    private final PayService payService;

    @Operation(summary = "Создание платежа")
    @PostMapping("/pay")
    public void pay(@RequestBody PayRequest request) {
        payService.pay(request);
    }
}
