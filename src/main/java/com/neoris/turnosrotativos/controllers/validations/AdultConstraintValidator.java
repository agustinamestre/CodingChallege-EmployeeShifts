package com.neoris.turnosrotativos.controllers.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AdultConstraintValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public void initialize(Adult adult) {
    }

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null) {
            return false;
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        return edad >= 18;
    }
}
