package com.mas.e_commerce_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "the request conflicts with the current state of the target resource.")
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}