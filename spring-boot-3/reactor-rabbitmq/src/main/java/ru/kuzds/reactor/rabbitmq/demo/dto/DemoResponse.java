package ru.kuzds.reactor.rabbitmq.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse {
    private String message;
    private Long value;
}
