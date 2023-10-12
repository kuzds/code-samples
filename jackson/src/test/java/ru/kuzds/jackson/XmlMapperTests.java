package ru.kuzds.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kuzds.jackson.dto.Patient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class XmlMapperTests {

	private final ObjectMapper objectMapper;

	public XmlMapperTests(@Qualifier("xmlObjectMapper") ObjectMapper xmlObjectMapper) {
		this.objectMapper = xmlObjectMapper;
	}

	@Test
	public void test() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		TypeReference<List<Patient>> reference = new TypeReference<>() {
		};

		List<Patient> patients;
		try (InputStream inputStream = classloader.getResourceAsStream("sample.xml")) {
			patients = objectMapper.readValue(inputStream, reference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(5, patients.size());
	}
}
