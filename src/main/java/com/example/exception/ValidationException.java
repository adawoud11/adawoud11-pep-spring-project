package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationException extends ResponseStatusException {

    public ValidationException(HttpStatus status) {
        super(status);
    }

    public ValidationException(HttpStatus status, String msg) {
        super(status, msg);

    }
}