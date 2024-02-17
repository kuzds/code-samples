package ru.kuzds.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @Schema(description = "Ошибки валидации")
    private final Map<String, String> errorsMap = new HashMap<>();

    public ErrorResponse(MethodArgumentNotValidException e) {
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

//        return e.getBindingResult().getFieldErrors().stream()
//                .map(fieldError -> String.format("Поле %s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
//                .findFirst().orElse("Неопознанная ошибка");
    }

    public ErrorResponse(ConstraintViolationException e) {
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errorsMap.put(getPropertyName(violation), violation.getMessage());
        }
    }

    private static String getPropertyName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        String[] pathArray = propertyPath.split("\\.");
        int lastIndex = pathArray.length - 1;
        if (lastIndex > -1) {
            return pathArray[lastIndex];
        } else {
            return "Н/О";
        }
    }
}
