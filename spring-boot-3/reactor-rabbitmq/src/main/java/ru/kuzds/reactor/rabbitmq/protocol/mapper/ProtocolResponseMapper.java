package ru.kuzds.reactor.rabbitmq.protocol.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.kuzds.reactor.rabbitmq.protocol.dto.ProtocolResponse;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.Context;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.ExceptionData;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.ExceptionType;
import ru.kuzds.reactor.rabbitmq.protocol.dto.ProtocolContext;

import static java.util.Objects.requireNonNullElse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtocolResponseMapper {

    private static final String UNDEFINED_ERROR = "Undefined error";

    public static <T> ProtocolResponse<T> createSuccess(@NonNull ProtocolContext payContext, @NonNull T body) {

        return ProtocolResponse.<T>builder()
                .context(mapToContext(payContext))
                .result(body)
                .resultType(body.getClass().getName())
                .build();
    }

    public static ProtocolResponse<Object> createBusinessError(ProtocolContext payContext, String message) {
        return createBusinessError(payContext, "500", message, null);
    }

    public static ProtocolResponse<Object> createBusinessError(ProtocolContext payContext, String code, String message) {
        return createBusinessError(payContext, code, message, null);
    }

    public static ProtocolResponse<Object> createBusinessError(@NonNull ProtocolContext payContext, @NonNull String code, String message, Object properties) {
        return ProtocolResponse.builder()
                .context(mapToContext(payContext))
                .exceptionData(ExceptionData.builder()
                        .exceptionType(ExceptionType.Error)
                        .code(code)
                        .message(requireNonNullElse(message, UNDEFINED_ERROR))
                        .properties(properties)
                        .build())
                .build();
    }

    public static ProtocolResponse<Object> createFatalError(@NonNull ProtocolContext payContext, String message) {
        return ProtocolResponse.builder()
                .context(mapToContext(payContext))
                .exceptionData(ExceptionData.builder()
                        .exceptionType(ExceptionType.Fatal)
                        .code("InternalError")
                        .message(requireNonNullElse(message, UNDEFINED_ERROR))
                        .build())
                .build();
    }

    public static Context mapToContext(ProtocolContext payContext) {
        return Context.builder()
                .commandType("")
                .operationId("")
                .correlationId(payContext.getCorrelationId())
                .build();
    }
}
