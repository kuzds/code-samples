package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class DebtorAccount {

    /**
     * 327	IBAN Клиента
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAcct.Id.IBAN
     */
    private String IBAN;

    /**
     * 30	Номер Счета Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAcct.Id.Othr.Id
     */
    private String id;

    /**
     * 125	Категория Средств Счета Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAcct.Id.Othr.SchmeNm.Prtry
     */
    private String proprietary;
}
