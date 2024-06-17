package ru.kuzds.reactor.rabbitmq.protocol.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalData {

    private Session session;

    private String isCommand;

    private String noCreateQueue;

    private String sourceServiceId;

}
