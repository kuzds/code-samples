package ru.kuzds.mockserver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "unavailable-service-client", url = "${ru.kuzds.unavailable-service.url}")
public interface UnavailableServiceClient {

    @GetMapping("/unavailable")
    String unavailable();
}
