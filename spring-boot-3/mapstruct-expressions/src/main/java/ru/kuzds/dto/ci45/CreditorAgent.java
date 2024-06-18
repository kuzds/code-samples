package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class CreditorAgent {
    /**
     * 305	Идентификатор SWIFT Банка Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.CdtrAgt.FinInstnId.BICFI
     */
    private String bicfi;

    /**
     * 95	Банковский Идентификационный Код Банка Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.CdtrAgt.FinInstnId.ClrSysMmbId.MmbId
     */
    private String memberIdentification;

    /**
     * 337	Страна Банка Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.CdtrAgt.FinInstnId.PstlAdr.Ctry
     */
    private String country;

    /**
     * 24	Идентификатор Банка Получателя
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.CdtrAgt.FinInstnId.Othr.MmbId
     */
    private String id;
}
