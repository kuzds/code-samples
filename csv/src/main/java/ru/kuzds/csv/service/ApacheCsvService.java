package ru.kuzds.csv.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.kuzds.csv.model.ApacheCsvLed;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static ru.kuzds.csv.helper.DataHelper.*;

@Slf4j
@Service
public class ApacheCsvService implements CsvService {

    private final static String filename = "apache";

    @Override
    public void test(InputStream is, String charsetName) throws Exception {
        List<ApacheCsvLed> list = readCsv(is, charsetName);

        Path listFile = Paths.get(listDir + filename + ".txt");
        Files.createDirectories(listFile.getParent());
        Files.writeString(listFile, formatted(list));

        Path resFile = Paths.get(resDir + filename + ".csv");
        Files.createDirectories(resFile.getParent());

        writeCsv(resFile.toString(), list);
    }

    @Override
    public List<ApacheCsvLed> readCsv(InputStream is, String charsetName) throws IOException {

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setIgnoreEmptyLines(true)
                .setRecordSeparator('\n')
                .setHeader().setSkipHeaderRecord(true)
                .setNullString("")
                .build();

        List<ApacheCsvLed> list = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, charsetName));
             CSVParser csvParser = new CSVParser(fileReader, csvFormat)
        ) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ApacheCsvLed led = ApacheCsvLed.builder()
                        .id(parse(csvRecord.get("no"), Integer::valueOf))
                        .brand(parse(csvRecord.get("brand"), String::trim))
                        .model(parse(csvRecord.get("model"), String::trim))
                        .power_l(parse(csvRecord.get("power_l"), Double::valueOf))
                        .p(parse(csvRecord.get("p"), Double::valueOf))
                        .color_l(parse(csvRecord.get("color_l"), Integer::valueOf))
                        .color(parse(csvRecord.get("color"), Integer::valueOf))
                        .lm_l(parse(csvRecord.get("lm_l"), Integer::valueOf))
                        .lm(parse(csvRecord.get("lm"), Integer::valueOf))
                        .barcode(parse(csvRecord.get("barcode"), String::trim))
                        .rub(parse(csvRecord.get("rub"), Double::valueOf))
                        .rating(parse(csvRecord.get("rating"), Double::valueOf))
                        .lamp_image(parse(csvRecord.get("lamp_image"), String::trim))
                        .u(parse(csvRecord.get("u"), String::trim))
                        .life(parse(csvRecord.get("life"), Integer::valueOf))
                        .build();

                list.add(led);
            }
        }
        return list;
    }

    @Override
    public void writeCsv(String path, List<?> list) throws IOException {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.builder()
                .setHeader("no", "brand", "model", "power_l", "p", "color_l", "color", "lm_l", "lm", "barcode", "rub", "rating", "lamp_image", "u", "life")
                .build();
        try (FileWriter fileWriter = new FileWriter(path);
             CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)
        ) {
            for (ApacheCsvLed o : ((List<ApacheCsvLed>)list)) {
                csvFilePrinter.printRecord(
                        o.getId(),
                        o.getBrand(),
                        o.getModel(),
                        o.getPower_l(),
                        o.getP(),
                        o.getColor_l(),
                        o.getColor(),
                        o.getLm_l(),
                        o.getLm(),
                        o.getBarcode(),
                        o.getRub(),
                        o.getRating(),
                        o.getLamp_image(),
                        o.getU(),
                        o.getLife()
                );
            }
        }
    }
}