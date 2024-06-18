package ru.kuzds.dto.ci45;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyAmount {
    /**
     * 302, 318	Сумма комиссии Банка
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.ChrgsInf.Amt
     */
    private BigDecimal value;
    /**
     * 333, 334	Валюта Суммы комиссии Банка
     */
    private String currency;
}
