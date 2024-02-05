package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosItensDTO;
import com.lipcam.PedidosApiSpring.services.PedidosService;
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
    public ResponseEntity add(@RequestBody PedidosDTO pedidosDTO) {
        if (pedidosDTO.getIdCliente() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe o Cliente"));

        if (pedidosDTO.getItens() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe itens"));

        if (pedidosDTO.getItens().toArray().length == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe itens"));

        return _service.addPedido(pedidosDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseDTO responseDTO = _service.deletePedido(id);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @PostMapping(value = "/itens/{id}")
    public ResponseEntity addItemPedido(@PathVariable Long id, @RequestBody PedidosItensDTO pedidosItensDTO) {
        if (pedidosItensDTO.getIdProduto() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe o Produto"));

        if (pedidosItensDTO.getQuantidade() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Informe a quantidade"));

        if (pedidosItensDTO.getQuantidade() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Quantidade deve ser maior que 0"));

        ResponseDTO responseDTO = _service.addItemPedido(id, pedidosItensDTO);
        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @DeleteMapping(value = "/itens/{idPedido}/{idItem}")
    public ResponseEntity deleteItemPedido(@PathVariable Long idPedido, @PathVariable Integer idItem) {
        ResponseDTO responseDTO = _service.deleteItemPedido(idPedido, idItem);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getPedidoById(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(_service.getPedidoById(id));
    }
}
