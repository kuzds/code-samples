package ru.kuzds.xml;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kuzds.xml.dto.Patient;
import ru.kuzds.xml.service.XmlPatientReader;

import java.io.InputStream;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class XmlApplication implements CommandLineRunner {

    private final XmlPatientReader reader;

    public static void main(String[] args) {
        SpringApplication.run(XmlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = classloader.getResourceAsStream("sample.xml")) {
            List<Patient> patients = reader.read(stream);
            log.info(patients.toString());
        }
    }
}
