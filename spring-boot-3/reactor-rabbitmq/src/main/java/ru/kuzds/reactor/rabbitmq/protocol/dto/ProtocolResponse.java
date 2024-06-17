package ru.kuzds.reactor.rabbitmq.protocol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.Context;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.ExceptionData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocolResponse<T> {

    /**
     * Тип ответа
     */
    private String resultType;

    /**
     * Тело ответа
     */
    private T result;

    private ExceptionData exceptionData;

    private Context context;
}
