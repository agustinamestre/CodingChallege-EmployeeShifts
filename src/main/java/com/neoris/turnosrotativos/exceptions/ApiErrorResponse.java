package com.neoris.turnosrotativos.exceptions;

import lombok.Data;

//esta es la estructura con la que se van a mostrar los errores

import java.time.LocalDateTime;
@Data
public class ApiErrorResponse {
    private String message;
    private Integer statusCode;
    private String statusName;
    private String path;
    private LocalDateTime timestamp;
}
