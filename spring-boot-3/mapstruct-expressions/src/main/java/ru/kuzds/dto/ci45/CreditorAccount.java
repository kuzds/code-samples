package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class CreditorAccount {
    /**
     * 31	Номер Счета Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.cdtrAcct.Id.Othr.Id
     */
    private String id;

    /**
     * 126	Категория Средств Счета Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.cdtrAcct.Id.Othr.SchmeNm.Prtry
     */
    private String proprietary ;
}
