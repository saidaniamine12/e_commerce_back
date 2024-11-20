package com.mas.e_commerce_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "validation failed or invalid request")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}