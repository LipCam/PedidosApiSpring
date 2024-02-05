package com.lipcam.PedidosApiSpring.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomMessageException extends RuntimeException{
    private HttpStatus httpStatus;

    public CustomMessageException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
}
