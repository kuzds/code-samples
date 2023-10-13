package ru.kuzds.validation.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
public class UUIDValidator implements ConstraintValidator<ValidateUUID, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        boolean result;
        try {
            result = value.equals(UUID.fromString(value).toString());
        } catch (Exception e) {
            return false;
        }

        return result;
    }
}
