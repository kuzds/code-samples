package ru.kuzds.dto.ci45;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Ci45BusinessApplicationHeader {
    /**
     * 308	Идентификатор Первого Банка-Посредника
     * 312	Идентификатор Второго Банка-Посредника
     * appHdr.rltd.fr.FIId.finInstnId.othr.id
     */
    private String fromFinancialInstitutionId;
    /**
     * 23	Идентификатор ОПКЦ СБП
     * appHdr.rltd.to.FIId.finInstnId.othr.id
     */
    private String toFinancialInstitutionId;
    /**
     * 27	Идентификатор Операции ОПКЦ СБП
     * appHdr.rltd.bizMsgIdr
     */
    private String businessMessageId;
    /**
     * 43	Схема Сообщения СБП
     * appHdr.rltd.msgDefIdr
     */
    private String messageDefinitionId;
    /**
     * 98	Тип Сообщения СБП
     * appHdr.rltd.bizSvc
     */
    private String businessService;
    /**
     * 328	Дата и Время Отправки Сообщения Первого Банка-Посредника
     * 345	Дата и Время Отправки от Второго Банка-Посредника
     * appHdr.rltd.creDt
     */
    private OffsetDateTime creationDate;
}
