package ru.kuzds.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kuzds.validation.validation.ValidateDateTime;
import ru.kuzds.validation.validation.ValidateString;
import ru.kuzds.validation.validation.ValidateUUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayRequest {

    @NotBlank
    @ValidateUUID
    private String paymentUUID;

    @NotBlank
    @Schema(description = "Номер карты")
    private String pan;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}?$", message = "недопустимый формат даты (YYMM)")
    @Schema(description = "Дата истечения срока действия карты в формате YYMM")
    private String exp;

    @NotBlank
    @ValidateString(acceptedValues={"bank_card", "uzcard", "mir_card", "humo"}, message = "недопустимое значение (bank_card, uzcard, mir_card, humo)")
    @Schema(description = "Метод оплаты (платежная система bank_card, uzcard, mir_card, humo)")
    private String method;

    @NotBlank
    @ValidateDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "недопустимый формат даты (yyyy-MM-dd HH:mm:ss)")
    @Schema(description = "Время создания платежа yyyy-MM-dd HH:mm:ss")
    private String initTime;
}
