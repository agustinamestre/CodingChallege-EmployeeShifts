package com.neoris.turnosrotativos.exceptions;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PropertyError implements Serializable {

    String property;
    String error;
    Object invalidValue;

    public PropertyError(FieldError err) {
        this.property = err.getField();
        this.error = err.getDefaultMessage();
        this.invalidValue = err.getRejectedValue();
    }

    public PropertyError(ConstraintViolation violation) {
        this.property = violation.getPropertyPath().toString();
        this.error = violation.getMessage();
        this.invalidValue = violation.getInvalidValue();
    }
}