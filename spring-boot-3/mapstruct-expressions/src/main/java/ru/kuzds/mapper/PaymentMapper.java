package ru.kuzds.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kuzds.dto.AddPaymentRequest;
import ru.kuzds.dto.Payment;
import ru.kuzds.dto.ci45.*;

import java.util.Optional;


@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "senderAccount",          expression = "java( senderAccount(request) )")
    @Mapping(target = "senderBankId",           expression = "java( senderBankId(request) )")
    @Mapping(target = "senderName",             expression = "java( name(debtor(request)) )")
    @Mapping(target = "creditorAccount",        expression = "java( creditorAccount(request) )")
    @Mapping(target = "recipientBankId",        expression = "java( recipientBankId(request) )")
    @Mapping(target = "recipientName",          expression = "java( name(creditor(request)) )")
    Payment toPayment(AddPaymentRequest request);

    default Optional<CreditTransferTransaction> creditTransferTransaction(AddPaymentRequest request) {
        return Optional.ofNullable(request.getCi45())
                .map(Ci45::getDocument)
                .map(Ci45Document::getCreditTransfer);
    }

    default DebtorAndCreditor debtor(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getDebtor)
                .orElse(null);
    }

    default DebtorAndCreditor creditor(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getCreditor)
                .orElse(null);
    }

    default String senderAccount(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getDebtorAccount)
                .map(DebtorAccount::getId)
                .orElse(null);
    }

    default String creditorAccount(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getCreditorAccount)
                .map(CreditorAccount::getId)
                .orElse(null);
    }

    default String senderBankId(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getDebtorAgent)
                .map(DebtorAgent::getId)
                .orElse(null);
    }

    default String recipientBankId(AddPaymentRequest request) {
        return creditTransferTransaction(request)
                .map(CreditTransferTransaction::getCreditorAgent)
                .map(CreditorAgent::getId)
                .orElse(null);
    }

    default String name(DebtorAndCreditor debtorAndCreditor) {
        return Optional.ofNullable(debtorAndCreditor)
                .map(DebtorAndCreditor::getName)
                .map(s -> s.replace("|", " "))
                .orElse(null);
    }
}
