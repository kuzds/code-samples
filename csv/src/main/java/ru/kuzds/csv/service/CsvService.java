package ru.kuzds.csv.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface CsvService {

    void test(InputStream is, String charsetName) throws Exception;
    List<?> readCsv(InputStream is, String charsetName) throws IOException;
    void writeCsv(String path, List<?> list) throws IOException;
}
