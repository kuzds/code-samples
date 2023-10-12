package ru.kuzds.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kuzds.jackson.dto.Gender;
import ru.kuzds.jackson.dto.Patient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonMapperTests {

	private final ObjectMapper objectMapper;

	public JsonMapperTests(@Qualifier("jsonObjectMapper") ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	private <T> T convertValue(Object fromValue, Class<T> toValueType) {
		return objectMapper.convertValue(fromValue, toValueType);
	}

	private <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
		return objectMapper.readValue(content, valueType);
	}

	@Test
	public void testOrderGenderDeserialization() throws JsonProcessingException {
		assertEquals(Gender.MALE, convertValue("М", Gender.class));
		assertEquals(Gender.FEMALE, convertValue("Ж", Gender.class));


		assertEquals(Gender.UNDEFINED, convertValue(null, Gender.class));
		assertEquals(Gender.UNDEFINED, convertValue("", Gender.class));
		assertEquals(Gender.UNDEFINED, convertValue("QWE", Gender.class));

		Patient patient;
		patient = readValue("{\"Gender\": \"М\"}", Patient.class);
		assertEquals(Gender.MALE, patient.getGender());

		patient = readValue("{\"Gender\": \"Ж\"}", Patient.class);
		assertEquals(Gender.FEMALE, patient.getGender());

		patient = readValue("{\"Gender\": \"\"}", Patient.class);
		assertEquals(Gender.UNDEFINED, patient.getGender());

		patient = readValue("{\"Gender\": \"Some\"}", Patient.class);
		assertEquals(Gender.UNDEFINED, patient.getGender());

		patient = readValue("{\"Gender\": null}", Patient.class);
		assertEquals(Gender.UNDEFINED, patient.getGender());

		patient = readValue("{}", Patient.class);
		assertEquals(Gender.UNDEFINED, patient.getGender()); // due to "public Gender gender = Gender.UNDEFINED;"
	}
}
