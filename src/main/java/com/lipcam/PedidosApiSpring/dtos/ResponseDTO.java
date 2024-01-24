package com.lipcam.PedidosApiSpring.dtos;

import lombok.Data;

@Data
public class ResponseDTO {
    String Result;
    String Message;

    public ResponseDTO(String result, String message) {
        Result = result;
        Message = message;
    }
}
