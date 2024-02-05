package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.cliente.AddEditClienteRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.cliente.ClientesDTO;
import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.entities.Clientes;
import com.lipcam.PedidosApiSpring.exceptions.CustomMessageException;
import com.lipcam.PedidosApiSpring.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClientesController {

    @Autowired
    ClientesService _service;

    @GetMapping
    public ResponseEntity findAll(@RequestBody Clientes entity) {
        return ResponseEntity.status(HttpStatus.OK).body(_service.findAll(entity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        ClientesDTO dto = _service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AddEditClienteRequestDTO addEditRequestDTO) {
        validaCampos(addEditRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(addEditRequestDTO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(@PathVariable Long id, @RequestBody AddEditClienteRequestDTO addEditRequestDTO) {
        validaCampos(addEditRequestDTO);
        _service.update(id, addEditRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Edição realizada com sucesso"));
    }

    private void validaCampos(AddEditClienteRequestDTO addEditRequestDTO) {
        if (addEditRequestDTO.getNome() == null)
            throw new CustomMessageException("Campo Nome deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getNome().isEmpty())
            throw new CustomMessageException("Campo Nome deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getCpf() == null)
            throw new CustomMessageException("Campo CPF deve ser preenchido", HttpStatus.BAD_REQUEST);

        if (addEditRequestDTO.getCpf().isEmpty())
            throw new CustomMessageException("Campo CPF deve ser preenchido", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        _service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK, "Exclusão realizada com sucesso"));
    }
}
