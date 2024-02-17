package ru.kuzds.mockserver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "mockserver-client", url = "${ru.kuzds.mockserver.url}")
public interface MockserverClient {

    @PostMapping("/payment")
    PaymentResponse payment(@RequestHeader(value = "mercId", required = false) String mercId,
                            @RequestHeader(value = "extId", required = false) String extId,
                            @RequestBody PaymentRequest request);
}
