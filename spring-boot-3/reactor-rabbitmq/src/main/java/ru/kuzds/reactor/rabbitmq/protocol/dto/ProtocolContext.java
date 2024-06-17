package ru.kuzds.reactor.rabbitmq.protocol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.reactor.rabbitmq.protocol.dto.type.AdditionalData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocolContext {
    private String correlationId;
    private AdditionalData additionalData;
}
