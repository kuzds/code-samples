package ru.kuzds.csv.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class DataHelper {

    public final static String listDir = "./out/list/";
    public final static String resDir = "./out/res/";

    public static String formatted(List<?> list) {
        StringBuilder res = new StringBuilder();
        for (Object object : list) {
            res.append(object.toString()).append("\n");
        }
        return res.toString();
    }
    public static  <T> T parse(String value, Function<String, T> func) {
        if (value == null || value.equals("") || value.equals("-")) {
            return null;
        }

        return func.apply(value);
    }
}
