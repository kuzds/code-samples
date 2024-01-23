package ru.kuzds.validation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = StringValidator.class)
@Documented
public @interface ValidateString {

    String[] acceptedValues();

    String message() default "недопустимое значение";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}