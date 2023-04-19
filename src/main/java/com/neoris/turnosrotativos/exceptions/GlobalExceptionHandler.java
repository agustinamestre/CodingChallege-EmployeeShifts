package com.neoris.turnosrotativos.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Aca me puse a buscar en internet la manera de mostrar mejor los errores.
    //La ajuste a lo que yo necesitaba.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(PropertyError::new)
                .collect(toList());

        //cada error muestra el campo que fallo, el mensaje de error, y el valor invalido.

        var error = new InvalidPropertiesError(errors, getPeticionURL(request));

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidConceptDataException.class, NoDataFoundException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.BAD_REQUEST);
    }

    //cree este metodo para no tener que setear los datos de ApiResponseError en cada excepcion
    private ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request, HttpStatus status) {
        ApiErrorResponse apiError = new ApiErrorResponse();
        apiError.setMessage(ex.getMessage());
        apiError.setStatusCode(status.value());
        apiError.setStatusName(status.getReasonPhrase());
        apiError.setPath(request.getDescription(false)
                                 .substring(4));
        apiError.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiError, status);
    }

    private String getPeticionURL(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}