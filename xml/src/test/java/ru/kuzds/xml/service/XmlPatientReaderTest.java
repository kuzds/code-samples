package ru.kuzds.xml.service;

import org.junit.jupiter.api.Test;
import ru.kuzds.xml.dto.Patient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlPatientReaderTest {
    @Test
    void shouldProperlySortByLastName() throws IOException {
        // given
        Patient patient1 = new Patient("Иван","Иванович","Иванов",
                "1980-06-01","male","+7-905-680-4476");
        Patient patient5 = new Patient("Михаил","Викторович","Медведев",
                "1995-02-15","male","+7-920-670-9643");
        // when
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = classloader.getResourceAsStream("sample.xml")) {
            List<Patient> patients = new XmlPatientReader().read(stream);

            // then
            assertEquals(patient1, patients.get(0));
            assertEquals(patient5, patients.get(4));
        }
    }
}