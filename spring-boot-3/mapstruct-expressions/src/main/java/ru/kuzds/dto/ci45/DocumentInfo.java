package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class DocumentInfo {

    /**
     * 349	Тип Набора Документации Связанной с Операцией
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocInf.LineDtls.Id.Tp.CdOrPrtry.Prtry
     */
    private String proprietary;

    /**
     * 344	Идентификатор банка, указавшего сумму списания/зачисления
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocInf.LineDtls.Id.Tp.Issr
     */
    private String issuer;

    /**
     * 355	Референсный идентификатор операции
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocInf.LineDtls.Id.Nb
     */
    private String number;
    /**
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocInf.LineDtls.Amt.RmtdAmt
     */
    private CurrencyAmount remittedAmount;
}
