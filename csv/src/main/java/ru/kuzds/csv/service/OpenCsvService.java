package ru.kuzds.csv.service;

import com.opencsv.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuzds.csv.model.OpenCsvLed;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.kuzds.csv.helper.DataHelper.*;

/**
 * https://opencsv.sourceforge.net/#parsing
 */
@Slf4j
@Service
public class OpenCsvService implements CsvService {

    private final static String filename = "opencsv";

    @Override
    public void test(InputStream is, String charsetName) throws Exception {
        List<OpenCsvLed> list = readCsv(is, charsetName);

        Path listFile = Paths.get(listDir + filename + ".txt");
        Files.createDirectories(listFile.getParent());
        Files.writeString(listFile, formatted(list));

        Path resFile = Paths.get(resDir + filename + ".csv");
        Files.createDirectories(resFile.getParent());

        writeCsv(resFile.toString(), list);
    }

    @Override
    public List<OpenCsvLed> readCsv(InputStream is, String charsetName) throws IOException {
        List<OpenCsvLed> list;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, charsetName))) {
            HeaderColumnNameMappingStrategy<OpenCsvLed> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(OpenCsvLed.class);
            CsvToBean<OpenCsvLed> csvToBean = new CsvToBeanBuilder<OpenCsvLed>(br)
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true).withIgnoreQuotations(true)
                    .build();

            list = csvToBean.parse();
        }
        return list;
    }

    @Override
    public void writeCsv(String path, List<?> list) throws IOException {

        // Create our strategy
        HeaderColumnNameMappingStrategy<OpenCsvLed> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(OpenCsvLed.class);

        // Build the header line which respects the declaration order
        String headerLine = Arrays.stream(OpenCsvLed.class.getDeclaredFields())
                .map(field -> field.getAnnotation(CsvCustomBindByName.class))
                .filter(Objects::nonNull)
                .map(CsvCustomBindByName::column)
                .collect(Collectors.joining(","));

        // Initialize strategy by reading a CSV with header only
        try (StringReader reader = new StringReader(headerLine)) {
            CsvToBean<OpenCsvLed> csv = new CsvToBeanBuilder<OpenCsvLed>(reader)
                    .withType(OpenCsvLed.class)
                    .withMappingStrategy(strategy)
                    .build();
            for (OpenCsvLed csvRow : csv) {
                // must have
            }
        }

        try (var writer = Files.newBufferedWriter(Paths.get(path))) {
            StatefulBeanToCsv<OpenCsvLed> csv = new StatefulBeanToCsvBuilder<OpenCsvLed>(writer)
                    .withMappingStrategy(strategy)
                    .withApplyQuotesToAll(false)
                    .build();
            csv.write((List<OpenCsvLed>) list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
