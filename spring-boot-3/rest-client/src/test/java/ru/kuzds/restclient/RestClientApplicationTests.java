package ru.kuzds.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.kuzds.restclient.dto.PaymentRequest;
import ru.kuzds.restclient.dto.PaymentResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@SpringBootTest
class RestClientApplicationTests {

	private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
			.parse("mockserver/mockserver")
			.withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());
	@Container
	private static final MockServerContainer CONTAINER = new MockServerContainer(MOCKSERVER_IMAGE);
	private static MockServerClient CLIENT;

	private static final EasyRandom GENERATOR = new EasyRandom();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestClient restClient;

	@BeforeAll
	public static void init() {
		CLIENT = new MockServerClient(CONTAINER.getHost(), CONTAINER.getServerPort());
	}

	@AfterAll
	public static void destroy() {
		CLIENT.close();
	}

	@Test
	void test_200() throws Exception {
		PaymentRequest paymentRequest = GENERATOR.nextObject(PaymentRequest.class);
		PaymentResponse expectedResponse = GENERATOR.nextObject(PaymentResponse.class);

		byte[] content = objectMapper.writeValueAsBytes(paymentRequest);
		CLIENT.when(request()
						.withMethod("POST")
						.withPath("/test")
						.withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
						.withBody(content))
				.respond(response()
						.withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
						.withStatusCode(HttpStatus.OK.value())
						.withBody(objectMapper.writeValueAsBytes(expectedResponse)));


		PaymentResponse actualResponse = restClient.post()
				.uri(CONTAINER.getEndpoint() + "/test")
				.contentType(APPLICATION_JSON)
				.body(paymentRequest)
				.retrieve()
				.body(new ParameterizedTypeReference<>() {
				});

		assertThat(actualResponse)
				.usingRecursiveComparison()
				.isEqualTo(expectedResponse);
	}
}
