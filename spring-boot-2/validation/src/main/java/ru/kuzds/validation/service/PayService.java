package ru.kuzds.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kuzds.validation.dto.PayRequest;

import javax.validation.Valid;

@Slf4j
@Validated // сервис должен получать объект валидации извне, не получится его распарсить в этом же сервисе
@Service
public class PayService {
    // Для валидации в контроллере достаточно @Valid
    public void pay(@Valid PayRequest request) {
        log.info(request.toString());
    }
}
