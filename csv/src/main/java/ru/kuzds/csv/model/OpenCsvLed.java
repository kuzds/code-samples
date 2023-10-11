package ru.kuzds.csv.model;

import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.csv.converter.DoubleCsvConverter;
import ru.kuzds.csv.converter.IntegerCsvConverter;
import ru.kuzds.csv.converter.StringCsvConverter;

@Data
@NoArgsConstructor
public class OpenCsvLed {
    @CsvCustomBindByName(column = "no", converter = IntegerCsvConverter.class)
    private Integer id;

    @CsvCustomBindByName(column = "brand", converter = StringCsvConverter.class)
    private String brand;

    @CsvCustomBindByName(column = "model", converter = StringCsvConverter.class)
    private String model;

    @CsvCustomBindByName(column = "power_l", converter = DoubleCsvConverter.class)
    private Double power_l;

    @CsvCustomBindByName(column = "p", converter = DoubleCsvConverter.class)
    private Double p;

    @CsvCustomBindByName(column = "color_l", converter = IntegerCsvConverter.class)
    private Integer color_l;

    @CsvCustomBindByName(column = "color", converter = IntegerCsvConverter.class)
    private Integer color;

    @CsvCustomBindByName(column = "lm_l", converter = IntegerCsvConverter.class)
    private Integer lm_l;

    @CsvCustomBindByName(column = "lm", converter = IntegerCsvConverter.class)
    private Integer lm;

    @CsvCustomBindByName(column = "barcode", converter = StringCsvConverter.class)
    private String barcode;

    @CsvCustomBindByName(column = "rub", converter = DoubleCsvConverter.class)
    private Double rub;

    @CsvCustomBindByName(column = "rating", converter = DoubleCsvConverter.class)
    private Double rating;

    @CsvCustomBindByName(column = "lamp_image", converter = StringCsvConverter.class)
    private String lamp_image;

    @CsvCustomBindByName(column = "u", converter = StringCsvConverter.class)
    private String u;

    @CsvCustomBindByName(column = "life", converter = IntegerCsvConverter.class)
    private Integer life;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", power_l=" + power_l +
                ", p=" + p +
                ", color_l=" + color_l +
                ", color=" + color +
                ", lm_l=" + lm_l +
                ", lm=" + lm +
                ", barcode='" + barcode + '\'' +
                ", rub=" + rub +
                ", rating=" + rating +
                ", lamp_image='" + lamp_image + '\'' +
                ", u='" + u + '\'' +
                ", life=" + life +
                '}';
    }
}
