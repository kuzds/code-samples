package ru.kuzds.dto.ci45;

import lombok.Data;

import java.util.List;

@Data
public class Remittance {
    /**
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf
     */
    private List<ReferredDocument> referredDocumentList;

    /**
     * 318	Сумма к зачислению Получателю
     * 334	Валюта Банка Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocAmt.DuePyblAmt
     */
    private CurrencyAmount duePayableAmount;
}
