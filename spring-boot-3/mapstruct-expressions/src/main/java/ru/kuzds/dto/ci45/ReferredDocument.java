package ru.kuzds.dto.ci45;

import lombok.Data;

import java.util.List;

@Data
public class ReferredDocument {
    /**
     * 348	Тип Набора Документации Связанной с Операцией
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf.Strd.RfrdDocInf.Tp.CdOrPrtry.Prtry
     */
    private String proprietary;
    /**
     * 349, 355, 348
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf
     */
    private List<DocumentInfo> documentInfoList;

}
