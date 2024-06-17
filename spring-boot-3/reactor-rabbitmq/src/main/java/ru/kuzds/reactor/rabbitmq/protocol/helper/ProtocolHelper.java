package ru.kuzds.reactor.rabbitmq.protocol.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Delivery;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;
import ru.kuzds.reactor.rabbitmq.protocol.dto.ProtocolResponse;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.Session;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.AdditionalData;
import ru.kuzds.reactor.rabbitmq.protocol.dto.ProtocolContext;
import ru.kuzds.reactor.rabbitmq.protocol.exception.ProtocolException;
import ru.kuzds.reactor.rabbitmq.protocol.mapper.ProtocolResponseMapper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

import static reactor.rabbitmq.ResourcesSpecification.*;

@Slf4j
public class ProtocolHelper {

    public static final String ADD_DATA_HEADER = "Additional-Data";
    public static final Map<String, String> OK_RESPONSE = Map.of("result", "ok");
    public static final Map<String, Object> DEAD_LETTER_EXCHANGE = Map.of("x-dead-letter-exchange", "dead-letter-exchange");
    private static final AtomicLong OPERATION_COUNTER = new AtomicLong(0);
    private final ObjectMapper objectMapper;
    private final Sender sender;
    private final Receiver receiver;

    public ProtocolHelper(ObjectMapper objectMapper, Sender sender, Receiver receiver) {
        this.objectMapper = objectMapper;
        this.sender = sender;
        this.receiver = receiver;
    }


    public static Consumer<Delivery> log(String queue) {
        return (Delivery delivery) -> {
            String data = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(">> {}: {}", queue, data);
        };
    }

    public <T> Function<Delivery, T> parseBodyTo(Class<T> tClass) {
        return delivery -> {
            try {
                return objectMapper.readValue(delivery.getBody(), tClass);
            } catch (Exception e) {
                throw new ProtocolException(e);
            }
        };
    }

    public ProtocolContext parseMqProperties(@NonNull AMQP.BasicProperties properties) {
        Map<String, Object> headers = properties.getHeaders();
        if (headers == null || headers.isEmpty()) {
            throw new IllegalArgumentException("Headers is empty");
        }

        String correlationId = properties.getCorrelationId();
        if (StringUtils.isBlank(correlationId)) {
            throw new IllegalArgumentException("Properties doesn't have correlationId");
        }

        if (headers.get(ADD_DATA_HEADER) == null) {
            throw new IllegalArgumentException("Required header '" + ADD_DATA_HEADER + "' is null");
        }
        String rawAddData = headers.get(ADD_DATA_HEADER).toString();
        if (StringUtils.isBlank(rawAddData)) {
            throw new IllegalArgumentException("Required header '" + ADD_DATA_HEADER + "' is empty");
        }

        AdditionalData addData = parseAdditionalData(rawAddData);

        return new ProtocolContext(correlationId, addData);
    }

    /**
     * Разобрать данные, которые пришли в заголовке 'Additional-Data'
     */
    public AdditionalData parseAdditionalData(String rawAddData) {
        try {
            AdditionalData info = objectMapper.readValue(rawAddData, AdditionalData.class);
            if (StringUtils.isBlank(info.getSourceServiceId())) {
                throw new IllegalArgumentException("Not found 'SourceServiceId' in 'Additional-Data'");
            }
            return info;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error while parsing header 'Additional-Data': " + rawAddData, ex);
        }
    }

    public AMQP.BasicProperties createProps(String targetExchange, @NonNull ProtocolContext payContext) {
        Map<String, Object> headers = createHeaders(payContext.getAdditionalData());

        return new AMQP.BasicProperties.Builder()
                .contentType(targetExchange)
                .deliveryMode(2) // 2 - Persistent, 1 - Transient
                .priority(1)
                .headers(headers)
                .correlationId(payContext.getCorrelationId())
                .build();
    }

    public Map<String, Object> createHeaders(AdditionalData additionalData) {
        Map<String, Object> headers = new HashMap<>();
        try {
            headers.put(ADD_DATA_HEADER, objectMapper.writeValueAsString(additionalData));
        } catch (JsonProcessingException e) {
            throw new ProtocolException(e);
        }
        return headers;
    }

    /**
     * @param commandRoutingKey routingKey очереди, куда отправляем исходящую команду
     * @param resultRoutingKey  routingKey очереди, куда ждем ответ на исходящую команду
     * @return builder параметров исходящей команды
     */
    public AMQP.BasicProperties.Builder createProducerProps(
            @NonNull String commandRoutingKey,
            @NonNull String resultRoutingKey,
            @NonNull Session session) {

        AdditionalData additionalData = new AdditionalData();
        additionalData.setSession(session);
        additionalData.setSourceServiceId(resultRoutingKey);

        Map<String, Object> headers = createHeaders(additionalData);

        return new AMQP.BasicProperties.Builder()
                .contentType(commandRoutingKey)
                .deliveryMode(2)
                .priority(1)
                .headers(headers)
                .timestamp(new Date())
                .correlationId(UUID.randomUUID().toString());
    }

    public String declareCommandQueue(@NonNull String routingKey) {
        String queueName = "Command_" + routingKey;

        sender.declareExchange(exchange(ProtocolExchange.COMMAND).durable(true))
                .then(sender.declareQueue(queue(queueName).durable(true).arguments(DEAD_LETTER_EXCHANGE)))
                .then(sender.bind(binding(ProtocolExchange.COMMAND, routingKey, queueName)))
                .block();

        return queueName;
    }

    public Mono<Void> success(@NonNull AMQP.BasicProperties properties) {
        return success(properties, OK_RESPONSE);
    }

    public Mono<Void> success(@NonNull AMQP.BasicProperties properties, Object response) {
        ProtocolContext payContext = parseMqProperties(properties);
        return success(payContext, response);
    }

    public Mono<Void> success(@NonNull ProtocolContext payContext) {
        return success(payContext, OK_RESPONSE);
    }

    public Mono<Void> success(@NonNull ProtocolContext payContext, Object response) {
        return sender.send(createOutboundMessage(ProtocolExchange.SUCCESS_RESPONSE, payContext,
                pc -> ProtocolResponseMapper.createSuccess(pc, response)));
    }

    public Mono<Void> fatal(@NonNull ProtocolContext payContext, String msg) {
        return sender.send(createOutboundMessage(ProtocolExchange.FAILED_RESPONSE, payContext,
                pc -> ProtocolResponseMapper.createFatalError(pc, msg)));
    }

    public Mono<Void> businessError(@NonNull AMQP.BasicProperties properties, String code, String msg) {
        ProtocolContext payContext = parseMqProperties(properties);
        return businessError(payContext, code, msg);
    }

    public Mono<Void> businessError(@NonNull ProtocolContext payContext, String code, String msg) {
        return sender.send(createOutboundMessage(ProtocolExchange.FAILED_RESPONSE, payContext,
                pc -> ProtocolResponseMapper.createBusinessError(pc, code, msg)));
    }

    private Mono<OutboundMessage> createOutboundMessage(String exchange, ProtocolContext payContext, Function<ProtocolContext, ProtocolResponse<Object>> mapper) {
        byte[] body;
        try {
            body = objectMapper.writeValueAsBytes(mapper.apply(payContext));
        } catch (JsonProcessingException e) {
            throw new ProtocolException(e);
        }
        AMQP.BasicProperties props = createProps(exchange, payContext);
        String queueName = payContext.getAdditionalData().getSourceServiceId();
        return Mono.just(new OutboundMessage(exchange, queueName, props, body));
    }

    /**
     * Выполнение команды в соответствии с протоколом
     */
    public Mono<Delivery> execCommand(String connectionName, String routingKey, byte[] body, Duration timeout) {
        String uniqSuffix = RandomStringUtils.randomAlphanumeric(10);
        Session session = Session.builder()
                .sessionId(connectionName + "-" + uniqSuffix) // неизвестно имеет ли значение уникальность sessionId
                .operationId(OPERATION_COUNTER.incrementAndGet())
                .build();

        String replyQueueName = connectionName + "_ProcessResult_" + uniqSuffix;

        String correlationId = UUID.randomUUID().toString();
        AMQP.BasicProperties props = createProducerProps(routingKey, replyQueueName, session)
                .correlationId(correlationId)
                .build();

        return sender.declareExchange(exchange(ProtocolExchange.SUCCESS_RESPONSE).durable(true))
                .then(sender.declareExchange(exchange(ProtocolExchange.FAILED_RESPONSE).durable(true)))
                .then(sender.declareQueue(queue(replyQueueName).autoDelete(true).arguments(ProtocolHelper.DEAD_LETTER_EXCHANGE)))
                .then(sender.bind(binding(ProtocolExchange.SUCCESS_RESPONSE, replyQueueName, replyQueueName)))
                .then(sender.bind(binding(ProtocolExchange.FAILED_RESPONSE, replyQueueName, replyQueueName)))

                // отправляем команду и ждем ответ
                .then(sender.send(Mono.just(new OutboundMessage(ProtocolExchange.COMMAND, routingKey, props, body)))
                        .then(receiver.consumeAutoAck(replyQueueName).next().timeout(timeout))
                        .doOnNext(delivery -> {
                            // проверка, что ответ пришел на наш соответствующий запрос
                            if (!correlationId.equals(delivery.getProperties().getCorrelationId())) {
                                throw new ProtocolException("Unknown response");
                            }
                        }));
    }
}
