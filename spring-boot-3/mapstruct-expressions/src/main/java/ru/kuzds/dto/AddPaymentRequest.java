package ru.kuzds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.dto.ci45.Ci45;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPaymentRequest {
    private String orderId;
    private String extId;
    private String inPaymentTrId;
    private String mercCurrency;
    private Ci45 ci45;
}
