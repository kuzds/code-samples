package ru.kuzds.mockserver;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private String extId;
    private Long amount;
    private String currency;
    private Long commission;
    private String paymentDetails;
    private BigDecimal rateBuy;
    private String paymentDetailsCategoryCode;
}
