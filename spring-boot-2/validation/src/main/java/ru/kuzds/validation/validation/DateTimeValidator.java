package ru.kuzds.validation.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateTimeValidator implements ConstraintValidator<ValidateDateTime, String> {

    private String pattern;
    @Override
    public void initialize(ValidateDateTime constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
