package ru.kuzds.reactor.rabbitmq.protocol.dto.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session implements Serializable {

    private String sessionId;

    private Long operationId;

    private Long authId;

    private String mercId;

}