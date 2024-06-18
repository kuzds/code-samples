package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class Ci45Document {

    /**
     * 321,343,328,345,нет,нет, 336,23
     */
    private GroupHeader groupHeader;

    private CreditTransferTransaction creditTransfer;

}
