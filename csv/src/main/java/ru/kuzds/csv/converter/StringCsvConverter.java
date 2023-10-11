package ru.kuzds.csv.converter;

import com.opencsv.bean.AbstractBeanField;

import static ru.kuzds.csv.helper.DataHelper.parse;

public class StringCsvConverter extends AbstractBeanField<String, Object> {

    @Override
    protected String convert(String value) {
        return parse(value, String::trim);
    }
}
