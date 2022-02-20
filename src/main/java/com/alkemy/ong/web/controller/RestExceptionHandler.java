package com.alkemy.ong.web.controller;

import com.alkemy.ong.web.exception.ApiErrorDTO;
import com.alkemy.ong.web.exception.FieldError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {FieldError.class})
    protected ResponseEntity<Object> handleFieldException(RuntimeException ex, WebRequest request) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Arrays.asList("field cannot be empty")
        );
        return handleExceptionInternal(ex, apiErrorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
