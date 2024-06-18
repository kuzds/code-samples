package ru.kuzds.dto.ci45;

import lombok.Data;


@Data
public class Charges {

    /**
     * 302	Сумма комиссии Банка
     * 333	Валюта Суммы комиссии Банка
     */
    private CurrencyAmount currencyAmount;

    /**
     * 91	Идентификатор документа Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgsInf.Agt.FinInstnId.Othr.Id
     */
    private String financialInstitutionId;

    /**
     * 90	Тип документа Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgsInf.Agt.FinInstnId.Othr.SchmeNm.Prtry
     */
    private String financialInstitutionProprietary;
}
