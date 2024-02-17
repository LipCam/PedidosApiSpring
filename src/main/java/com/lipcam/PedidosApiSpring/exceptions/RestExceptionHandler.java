package com.lipcam.PedidosApiSpring.exceptions;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler  {

    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity customMessageException(CustomMessageException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(new ResponseDTO(ex.getHttpStatus(), ex.getMessage()));
    }

    @ExceptionHandler(RegistroInexistenteException.class)
    public ResponseEntity registroInexistenteException(RegistroInexistenteException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodNotValidException(MethodArgumentNotValidException ex) {
        List<String> lstErros = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage()).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, String.join(", ", lstErros)));
    }


}
