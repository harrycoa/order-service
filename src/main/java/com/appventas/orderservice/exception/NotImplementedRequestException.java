package com.appventas.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedRequestException extends RuntimeException {
    public NotImplementedRequestException(String message){
        super(message);
    }
}

