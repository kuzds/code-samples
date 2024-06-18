package ru.kuzds.dto.ci45;

import lombok.Data;

import java.util.List;

@Data
public class DebtorAndCreditor {

    /**
     * 50/51	Фамилия Имя и Отчество Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.Nm
     */
    private String name;
    /**
     * нет	константа "HOME"
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.AdrTp.Cd
     */
    private AddressTypeCode code;

    /**
     * 378	Адрес Корпус
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.Dept
     */
    private String department;

    /**
     * 379	Адрес Строение
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.subDept
     */
    private String subDepartment;


    /**
     * 376	Адрес Улица
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.StrtNm
     */
    private String streetName;

    /**
     * 377	Адрес Номер Дома
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.BldgNb
     */
    private String buildingNumber;

    /**
     * 380	Адрес Номер Квартиры
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.Room
     */
    private String room;

    /**
     * 370	Адрес Почтовый Индекс
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.PstCd
     */
    private String postCode;

    /**
     * 374	Адрес Город
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.TwnNm
     */
    private String townName;

    /**
     * 373	Адрес Место нахождения в городе
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.TwnLctnNm
     */
    private String townLocationName;

    /**
     * 375	Адрес Район
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.DstrctNm
     */
    private String districtName;

    /**
     * 372	Адрес Территориальная единица страны
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.CtrySubDvsn
     */
    private String countrySubDivision;

    /**
     * 371	Адрес Страна
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.Ctry
     */
    private String country;

    /**
     * 1	Адрес Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.AdrLine
     */
    private List<String> addressLine;

    /**
     * 21,46,96,91,90,362
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.PstlAdr.PrvtId
     */
    private List<PersonInfo> personList;

    /**
     * 325	Страна Клиента
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.CtryOfRes
     */
    private String countryOfResidence;

    /**
     * 75/74	PAM Плательщика
     * document.FIToFIPmtStsRpt.cdtTrfTxInf.dbtr.CtctDtls
     */
    private String contactDetailsName;
}
