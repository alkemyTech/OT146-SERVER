package com.alkemy.ong.web.exception;

public class FieldError extends RuntimeException {
    public FieldError(String error) {
        super(error);
    }
}
