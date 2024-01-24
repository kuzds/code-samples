package ru.kuzds.easy.random;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EasyRandomApplicationTests {

	@Test
	void contextLoads() {
		Payment payment = EasyRandomHelper.getPaymentRequest();

		assertThat(payment).isNotNull();
		assertThat(payment.getRequestId()).isNull();
		assertThat(payment.getOrderId()).isNotNull();
		assertThat(payment.getDate()).isNotNull();
		assertThat(payment.getRate()).isNotNull();
		assertThat(payment.getDirection()).isNotNull();
		assertThat(payment.getAmountSender()).isNotNull();

		log.info(payment.toString());
	}
}
