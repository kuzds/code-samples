package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class RemittanceAmountDue {
    /**
     * 318,334
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf
     */
    private CurrencyAmount duePayableAmount;
}
