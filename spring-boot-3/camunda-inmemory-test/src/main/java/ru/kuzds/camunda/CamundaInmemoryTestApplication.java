package ru.kuzds.camunda;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Deployment(resources = {"classpath*:/bpmn/*.bpmn", "classpath*:/bpmn/*.dmn"})
public class CamundaInmemoryTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaInmemoryTestApplication.class, args);
	}

}
