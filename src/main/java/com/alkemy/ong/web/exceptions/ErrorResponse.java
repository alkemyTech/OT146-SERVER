package com.alkemy.ong.web.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String uri;

    public ErrorResponse(HttpStatus status, String message, String uri) {
        this.status = status.value();
        this.message = message;
        this.uri = uri;
    }
}
