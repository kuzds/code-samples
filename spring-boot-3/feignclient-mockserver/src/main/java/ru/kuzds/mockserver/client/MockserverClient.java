package ru.kuzds.mockserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.kuzds.mockserver.dto.PaymentRequest;
import ru.kuzds.mockserver.dto.PaymentResponse;


@FeignClient(value = "mockserver-client", url = "${ru.kuzds.mockserver.url}", configuration = RequestLoggerClientConfig.class)
public interface MockserverClient {

    @PostMapping("/payment")
    PaymentResponse payment(@RequestHeader(value = "mercId", required = false) String mercId,
                            @RequestHeader(value = "extId", required = false) String extId,
                            @RequestBody PaymentRequest request);
}
