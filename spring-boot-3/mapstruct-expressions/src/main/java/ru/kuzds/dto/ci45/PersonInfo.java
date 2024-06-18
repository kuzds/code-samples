package ru.kuzds.dto.ci45;

import lombok.Data;

@Data
public class PersonInfo {
    /**
     * 91	Идентификатор документа Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgsInf.Agt.FinInstnId.Othr.Id
     */
    private String id;

    private PersonSchemeName schmeNm;
    /**
     * 362	Орган, выдавший документ
     * document.FIToFIPmtStsRpt.grpHdr.msgId
     */
    private String issr;
}
