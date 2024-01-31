package com.lipcam.PedidosApiSpring.controllers;

import com.lipcam.PedidosApiSpring.dtos.cliente.AddEditClienteRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.cliente.ClientesDTO;
import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.entities.Clientes;
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
    public ResponseEntity findById(@PathVariable Integer id) {
        ClientesDTO dto = _service.findById(id);
        if (dto != null)
            return ResponseEntity.status(HttpStatus.OK).body(dto);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Registro inexistente"));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody AddEditClienteRequestDTO addEditRequestDTO) {
        ResponseEntity responseEntity = validaCampos(addEditRequestDTO);
        if(responseEntity != null)
            return responseEntity;

        return _service.add(addEditRequestDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody AddEditClienteRequestDTO addEditRequestDTO) {
        ResponseEntity responseEntity = validaCampos(addEditRequestDTO);
        if(responseEntity != null)
            return responseEntity;

        ResponseDTO responseDTO = _service.update(id, addEditRequestDTO);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    private ResponseEntity validaCampos(AddEditClienteRequestDTO addEditRequestDTO){
        if (addEditRequestDTO.getNome() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Nome deve ser preenchido"));

        if (addEditRequestDTO.getNome().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo Nome deve ser preenchido"));

        if (addEditRequestDTO.getCpf() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo CPF deve ser preenchido"));

        if (addEditRequestDTO.getCpf().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Campo CPF deve ser preenchido"));

        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        ResponseDTO responseDTO = _service.delete(id);

        if (responseDTO.getResult().equals("OK"))
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
}
