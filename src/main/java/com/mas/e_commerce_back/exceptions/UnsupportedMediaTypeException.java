package com.mas.e_commerce_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason = "the content of the body is not supported by the server")
public class UnsupportedMediaTypeException extends RuntimeException{
    public UnsupportedMediaTypeException(String message){
        super(message);
    }
}
