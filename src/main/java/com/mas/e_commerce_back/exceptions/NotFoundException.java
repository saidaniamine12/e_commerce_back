package com.mas.e_commerce_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "resource not found")
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
