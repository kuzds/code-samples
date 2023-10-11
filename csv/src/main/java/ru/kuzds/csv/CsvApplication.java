package ru.kuzds.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kuzds.csv.service.CsvService;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class CsvApplication implements CommandLineRunner {

    private final List<CsvService> csvServices;

    public static void main(String[] args) {
        SpringApplication.run(CsvApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//		try (InputStream file = classloader.getResourceAsStream("Led.csv")) {

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ws-wcg", 18080));
        for (CsvService csvService : csvServices) {
            try (InputStream file = new URL("http://lamptest.ru/led.csv").openConnection(proxy).getInputStream()) {
                csvService.test(file, "windows-1251");
            } catch (IOException e) {
                log.error("При обработке CSV файла произошла ошибка", e);
            }
        }
    }
}
