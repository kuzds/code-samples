package ru.kuzds.easy.random;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@FieldNameConstants
public class Payment {

    private UUID requestId;
    private UUID orderId;
    private Date date;
    private String direction;
    private Long amountSender;
    private BigDecimal rate;
}
