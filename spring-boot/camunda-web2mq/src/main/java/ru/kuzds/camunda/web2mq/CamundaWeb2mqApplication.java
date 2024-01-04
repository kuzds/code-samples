package ru.kuzds.camunda.web2mq;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources = "classpath*:/bpmn/*.bpmn")
public class CamundaWeb2mqApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaWeb2mqApplication.class, args);
	}

}
