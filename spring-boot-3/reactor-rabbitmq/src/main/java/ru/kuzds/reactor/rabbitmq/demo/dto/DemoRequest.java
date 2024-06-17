package ru.kuzds.reactor.rabbitmq.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DemoRequest {
    private String message;
    private Long value;
}
