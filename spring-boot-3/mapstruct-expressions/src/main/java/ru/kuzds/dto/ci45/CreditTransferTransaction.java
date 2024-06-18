package ru.kuzds.dto.ci45;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CreditTransferTransaction {
    /**
     * 342	Идентификатор операции ПСП
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.PmtId.InstrId
     */
    private String instructionId;

    /**
     * 369	Идентификатор платежного поручения Плательщика
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.PmtId.EndToEndId
     */
    private String endToEndId;

    /**
     * 27	Идентификатор Операции ОПКЦ СБП
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.pmtId.txId
     */
    private String transactionId;

    /**
     * 49	Тип сценария по Операции СБП
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.PmtTpInf.SvcLvl.Prtry
     */
    private String serviceLevel;

    /**
     * 48	Тип Операции СБП
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.PmtTpInf.CtgyPurp.Prtry
     */
    private String categoryPurposeProprietary;

    /**
     * 44	Сумма Операции СБП
     * 9	Валюта операции
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.IntrBkSttlmAmt
     */
    private CurrencyAmount interbankSettlementAmount;

    /**
     * 341	Дата и время операции Платежной Системы Партнера
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.SttlmTmIndctn.DbtDtTm
     */
    private OffsetDateTime debitDateTime;

    /**
     * 364	Время события для контроля
     * * document.FIToFIPmtStsRpt.cdtTrfTxInf.SttlmTmReq.RjctTm
     */
    private OffsetDateTime rejectTime;

    /**
     * 99	Метка времени Операции СБП
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.AccptncDtTm
     */
    private OffsetDateTime acceptanceDateTime;

    /**
     * 316	Сумма к списанию с Плательщика
     * 331	Валюта Банка Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.InstdAmt
     */
    private CurrencyAmount instructedAmount;

    /**
     * 300	Курс конвертации валюты
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.XchgRate
     */
    private BigDecimal exchangeRate;

    /**
     * 301	Плательщик комиссии
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgBr
     */
    private ChargeBearer chargeBearer;
    /**
     * 302, 333, 303, 322
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgsInf
     */
    private List<Charges> charges;

    /**
     * 306, 323, 309,308,310,311,
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt1
     */
    private PreviousAgent previousAgent1;
    /**
     *307,324,312
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.PrvsInstgAgt2
     */
    private PreviousAgent previousAgent2;

    /**
     * 50,нет,376,377,380,370,374,373,375,372,371,1,21,46,96,ytn,91,90,362,325,75
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr
     */
    private DebtorAndCreditor debtor;

    /**
     * 327, 30, 125
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAcct
     */
    private DebtorAccount debtorAccount;

    /**
     * 304,346,22
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.DbtrAgt
     */
    private DebtorAgent debtorAgent;

    /**
     * 305,95,337,24
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.CdtrAgt
     */
    private CreditorAgent creditorAgent;

    /**
     * 305sdasd
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.Cdtr
     */
    private DebtorAndCreditor creditor;

    /**
     * 31,126
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.cdtrAcct
     */
    private CreditorAccount creditorAccount;

    /**
     * -	Константа "VOCode"
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RgltryRptg.Dtls.Tp
     */
    private String regulatoryReportingType;

    /**
     * 363	Код категории перевода
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RgltryRptg.Dtls.Cd
     */
    private String regulatoryReportingCode;

    /**
     * 348,349,355,348,320,344,319,332,318,334
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.RmtInf
     */
    private Remittance remittance;

}
