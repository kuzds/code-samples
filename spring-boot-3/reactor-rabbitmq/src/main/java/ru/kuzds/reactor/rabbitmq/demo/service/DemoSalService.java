package ru.kuzds.reactor.rabbitmq.demo.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;
import ru.kuzds.reactor.rabbitmq.demo.dto.DemoRequest;
import ru.kuzds.reactor.rabbitmq.demo.dto.DemoResponse;
import ru.kuzds.reactor.rabbitmq.protocol.helper.ProtocolHelper;


@Slf4j
@Service
@RequiredArgsConstructor
public class DemoSalService {

    private final Receiver receiver;
    private final ProtocolHelper protocolHelper;

    @Value("${protocol.routing-key.demo}")
    private String routingKey;

    @PostConstruct
    private void consume() {
        String queueName = protocolHelper.declareCommandQueue(routingKey);

        receiver.consumeAutoAck(queueName)
                .flatMap(delivery -> Mono.just(delivery)
                        .doOnNext(ProtocolHelper.log(queueName))
                        .map(protocolHelper.parseBodyTo(DemoRequest.class))
                        .flatMap(request -> {
                            DemoResponse response = new DemoResponse(request.getMessage(), request.getValue());
                            return protocolHelper.success(delivery.getProperties(), response);
                        })
                        .onErrorResume(Exception.class, e -> {
                            log.error(e.getMessage(), e);
                            return protocolHelper.businessError(delivery.getProperties(), "500", e.getMessage());
                        })
                )
                .subscribe();
    }
}
