package com.lipcam.PedidosApiSpring.exceptions;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity customMessageException(CustomMessageException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ResponseDTO(ex.getHttpStatus(), ex.getMessage()));
    }

    @ExceptionHandler(RegistroInexistenteException.class)
    public ResponseEntity registroInexistenteException(RegistroInexistenteException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
    }
}
