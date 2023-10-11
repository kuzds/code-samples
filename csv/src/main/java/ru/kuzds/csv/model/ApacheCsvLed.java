package ru.kuzds.csv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApacheCsvLed {
  private Integer id;
  private String brand;
  private String model;
  private Double power_l;
  private Double p;
  private Integer color_l;
  private Integer color;
  private Integer lm_l;
  private Integer lm;
  private String barcode;
  private Double rub;
  private Double rating;
  private String lamp_image;
  private String u;
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
