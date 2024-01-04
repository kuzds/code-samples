package ru.kuzds.rsocket.server.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GratuityIn {
    private BigDecimal billTotal;
    private int percent;
}