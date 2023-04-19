package com.neoris.turnosrotativos.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidPropertiesError implements Serializable {

    String error;
    Integer statusCode;
    List<PropertyError> errors;
    String path;
    LocalDateTime timestamp;

    //Esta es la estructura de como se van a mostrar los errores
    public InvalidPropertiesError(List<PropertyError> errors, String path) {
        this.error = "Bad Request";
        this.statusCode = 400;
        this.errors = errors;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
