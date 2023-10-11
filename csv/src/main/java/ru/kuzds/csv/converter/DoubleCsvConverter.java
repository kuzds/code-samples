package ru.kuzds.csv.converter;

import com.opencsv.bean.AbstractBeanField;

import static ru.kuzds.csv.helper.DataHelper.parse;

public class DoubleCsvConverter extends AbstractBeanField<Double, Object> {

    @Override
    protected Double convert(String value) {
        return parse(value, Double::valueOf);
    }
}
