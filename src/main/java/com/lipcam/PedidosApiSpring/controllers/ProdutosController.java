package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.AddEditProdutoRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.ProdutosDTO;
import com.lipcam.PedidosApiSpring.entities.Produtos;
import com.lipcam.PedidosApiSpring.exceptions.CustomMessageException;
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
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AddEditProdutoRequestDTO addEditRequestDTO) {
        validaCampos(addEditRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(addEditRequestDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(@PathVariable Long id, @RequestBody AddEditProdutoRequestDTO addEditRequestDTO) {
        validaCampos(addEditRequestDTO);
        _service.update(id, addEditRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Edição realizada com sucesso"));
    }

    private void validaCampos(AddEditProdutoRequestDTO addEditRequestDTO) {
        if (addEditRequestDTO.getDescricao() == null)
            throw new CustomMessageException("Campo Descrição deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getDescricao().isEmpty())
            throw new CustomMessageException("Campo Descrição deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getPreco() == null)
            throw new CustomMessageException("Campo Preço deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getPreco().compareTo(new BigDecimal(0)) < 0)
            throw new CustomMessageException("Campo Preço deve ser maior que 0", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        _service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Exclusão realizada com sucesso"));
    }
}
