package com.mas.e_commerce_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INSUFFICIENT_STORAGE, reason = "the server does not have the sufficient storage space to complete the request")

public class InsufficientStorageException extends RuntimeException{
    public InsufficientStorageException(String message){
        super(message);
    }
}
