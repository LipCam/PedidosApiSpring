package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.AddEditProdutoRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.ProdutosDTO;
import com.lipcam.PedidosApiSpring.entities.Produtos;
import com.lipcam.PedidosApiSpring.services.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutosController {

    @Autowired
    ProdutosService _service;

    @GetMapping
    public ResponseEntity findAll(@RequestBody Produtos entity) {
        return ResponseEntity.status(HttpStatus.OK).body(_service.findAll(entity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        ProdutosDTO dto = _service.findById(id);
        if (dto != null)
            return ResponseEntity.status(HttpStatus.OK).body(dto);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Registro inexistente"));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AddEditProdutoRequestDTO addEditRequestDTO) {
        ResponseEntity responseEntity = validaCampos(addEditRequestDTO);
        if(responseEntity != null)
            return responseEntity;

        return _service.add(addEditRequestDTO);
    }

    private ResponseEntity validaCampos(AddEditProdutoRequestDTO addEditRequestDTO){
        if (addEditRequestDTO.getDescricao() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Descrição deve ser preenchido"));

        if (addEditRequestDTO.getDescricao().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Descrição deve ser preenchido"));

        if (addEditRequestDTO.getPreco() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Preço deve ser preenchido"));

        if (addEditRequestDTO.getPreco().compareTo(new BigDecimal(0)) < 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Preço deve ser maior que 0"));

        return null;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(@PathVariable Long id, @RequestBody AddEditProdutoRequestDTO addEditRequestDTO) {
        ResponseEntity responseEntity = validaCampos(addEditRequestDTO);
        if(responseEntity != null)
            return responseEntity;

        ResponseDTO responseDTO = _service.update(id, addEditRequestDTO);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseDTO responseDTO = _service.delete(id);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
