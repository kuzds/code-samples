package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class PreviousAgent {
    /**
     * 306/307	Идентификатор SWIFT Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1.FinInstnId.BICFI
     */
    private String bicfi;

    /**
     * 323/324	БИК Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1.FinInstnId.ClrSysMmbId.MmbId
     */
    private String memberId;

    /**
     * 309	Название Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1.FinInstnId.Nm
     */
    private String name;

    /**
     * 308/312	Идентификатор Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1.FinInstnId.Othr.Id
     */
    private String bankId;

    /**
     * 310	Номер счета Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1Acct.Id.Othr.Id
     */
    private String accountId;

    /**
     * 311	Валюта счета Первого Банка-Посредника
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1Acct.Id.Ccy
     */
    private String accountCurrency;

}
