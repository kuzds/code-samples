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
@Constraint(validatedBy = UUIDValidator.class)
@Documented
public @interface ValidateUUID {


    String message() default "некорректный UUID";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}