package ru.kuzds.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Payment {

    private String senderAccount;
    private String senderBankId;
    private String senderName;
    private String creditorAccount;
    private String recipientBankId;
    private String recipientName;
}
