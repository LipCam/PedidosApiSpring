package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.cliente.AddEditClienteRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.cliente.ClientesDTO;
import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.entities.Clientes;
import com.lipcam.PedidosApiSpring.repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository _repository;

    public List<Clientes> findAll(Clientes entity)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(entity, exampleMatcher);

        return _repository.findAll(example);
    }

    public ClientesDTO findById(Long Id)
    {
        Clientes entity =_repository.findById(Id).orElse(null);
        if(entity != null)
            return new ClientesDTO(entity);
        return null;
    }

    @Transactional
    public ResponseEntity add(AddEditClienteRequestDTO addEditRequestDTO) {
        Clientes entity = _repository.findByCpf(addEditRequestDTO.getCpf());
        if (entity != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Cliente já existente com este CPF"));

        entity = _repository.save(new Clientes(addEditRequestDTO.getNome(), addEditRequestDTO.getCpf()));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @Transactional
    public ResponseDTO update(Long id, AddEditClienteRequestDTO addEditRequestDTO)
    {
        Clientes entity = _repository.findById(id).orElse(null);
        if(entity != null)
        {
            entity.setNome(addEditRequestDTO.getNome());
            entity.setCpf(addEditRequestDTO.getCpf());
            _repository.save(entity);
            return new ResponseDTO("OK", "Edição realizada com sucesso");
        }
        return new ResponseDTO("Erro", "Registro inexistente");
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        Clientes entity = _repository.findById(id).orElse(null);

        if(entity != null) {
            _repository.delete(entity);
            return new ResponseDTO("OK", "Exclusão realizada com sucesso");
        }
        return new ResponseDTO("Erro", "Registro inexistente");
    }
}
