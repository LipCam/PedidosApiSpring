package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosDTO;
import com.lipcam.PedidosApiSpring.services.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidosController {

    @Autowired
    PedidosService _service;

    @PostMapping
    public ResponseEntity add(@RequestBody PedidosDTO pedidosDTO) {
        if (pedidosDTO.getIdCliente() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe o Cliente"));

        if (pedidosDTO.getItens() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe itens"));

        if (pedidosDTO.getItens().toArray().length == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe itens"));

        return _service.addPedido(pedidosDTO);
    }
}
