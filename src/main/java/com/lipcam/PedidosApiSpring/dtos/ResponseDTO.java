package com.lipcam.PedidosApiSpring.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    HttpStatus status;
    String message;

    public ResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
