package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class DebtorAgent {
    /**
     * 304	Идентификатор SWIFT Банка Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAgt.FinInstnId.BICFI
     */
    private String bicfi;

    /**
     * 346	Страна Банка Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAgt.FinInstnId.PstlAdr.Ctry
     */
    private String country;

    /**
     * 22	Идентификатор Банка Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAgt.FinInstnId.Othr.Id
     */
    private String id;
}
