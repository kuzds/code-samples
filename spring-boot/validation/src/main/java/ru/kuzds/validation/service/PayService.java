package ru.kuzds.validation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kuzds.validation.dto.PayRequest;

import javax.validation.Valid;

@Slf4j
@Validated
@Service
public class PayService {

    public void pay(@Valid PayRequest request) {
        log.info(request.toString());
    }
}
