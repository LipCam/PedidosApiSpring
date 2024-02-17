package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosItensDTO;
import com.lipcam.PedidosApiSpring.entities.PedidosItens;
import com.lipcam.PedidosApiSpring.exceptions.CustomMessageException;
import com.lipcam.PedidosApiSpring.services.PedidosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidosController {

    @Autowired
    PedidosService _service;

    @PostMapping
    public ResponseEntity add(@RequestBody @Valid PedidosDTO pedidosDTO) {
        if (pedidosDTO.getItens() == null)
            throw new CustomMessageException("Informe itens", HttpStatus.BAD_REQUEST);

        if (pedidosDTO.getItens().toArray().length == 0)
            throw new CustomMessageException("Informe itens", HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(_service.addPedido(pedidosDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        _service.deletePedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Exclusão realizada com sucesso"));
    }

    @PostMapping(value = "/itens/{id}")
    public ResponseEntity addItemPedido(@PathVariable Long id, @RequestBody @Valid PedidosItensDTO pedidosItensDTO) {
        PedidosItens pedidosItens = _service.addItemPedido(id, pedidosItensDTO);
        pedidosItensDTO.setItemId(pedidosItens.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosItensDTO);
    }

    @DeleteMapping(value = "/itens/{idPedido}/{idItem}")
    public ResponseEntity deleteItemPedido(@PathVariable Long idPedido, @PathVariable Integer idItem) {
        _service.deleteItemPedido(idPedido, idItem);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Exclusão realizada com sucesso"));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getPedidoById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(_service.getPedidoById(id));
    }
}
