package ru.kuzds.activemq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ActivemqApplication {

	@Value("${kuzds.queue.to}")
	private String queueTo;

	private final JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ActivemqApplication.class, args);
	}

	@JmsListener(destination = "${kuzds.queue.from}")
	public void receiveOrder(String data) {
		jmsTemplate.convertAndSend(queueTo, data);
	}
}
