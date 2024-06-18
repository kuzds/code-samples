package ru.kuzds.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kuzds.dto.AddPaymentRequest;
import ru.kuzds.dto.Payment;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    private static final EasyRandom GENERATOR = new EasyRandom();
    @Test
    @DisplayName("маппинг payment")
    void test1() {

        AddPaymentRequest addPaymentRequest = GENERATOR.nextObject(AddPaymentRequest.class);

        Payment payment = PaymentMapper.INSTANCE.toPayment(addPaymentRequest);

        assertThat(payment)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
        ;
    }
}