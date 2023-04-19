package com.neoris.turnosrotativos.controllers.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AdultConstraintValidator.class)
public @interface Adult {
    String message() default "La edad del empleado no puede ser menor a 18 años.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
