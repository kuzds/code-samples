package ru.kuzds.dto.ci45;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GroupHeader {

    /**
     * 321	Уникальный Номер Сообщения Первого Банка Посредника
     * 343	Уникальный Номер Сообщения от Второго Банка-Посредника
     * document.FIToFIPmtStsRpt.grpHdr.msgId
     */
    private String messageId;
    /**
     * 328	Дата и Время Отправки Сообщения Первого Банка-Посредника
     * 345	Дата и Время Отправки от Второго Банка-Посредника
     * document.FIToFIPmtStsRpt.grpHdr.creDtTm
     */
    private OffsetDateTime creationDateTime;

    /**
     * нет	константа "1"
     * document.FIToFIPmtStsRpt.grpHdr.nbOfTxs
     */
    private String numberOfTransactions;

    /**
     * нет	константа "CLRG"
     * document.FIToFIPmtStsRpt.grpHdr.sttlmInf.sttlmMtd
     */
    private Settlement settlementMethod;

    /**
     * 336	Скрипт
     * document.FIToFIPmtStsRpt.grpHdr.pmtTpInf.svcLvl.prtry
     */
    private String serviceLevel;

    /**
     * 23	Идентификатор ОПКЦ СБП
     * document.FIToFIPmtStsRpt.grpHdr.pmtTpInf.lclInstrm.prtry
     */
    private String localInstrumentProprietary;
}
