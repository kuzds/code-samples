package ru.kuzds.csv.converter;

import com.opencsv.bean.AbstractBeanField;

import static ru.kuzds.csv.helper.DataHelper.parse;

public class IntegerCsvConverter extends AbstractBeanField<Integer, Object> {

    @Override
    protected Integer convert(String value) {
        return parse(value, Integer::valueOf);
    }
}
