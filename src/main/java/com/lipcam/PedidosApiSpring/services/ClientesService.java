package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.cliente.AddEditClienteRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.cliente.ClientesDTO;
import com.lipcam.PedidosApiSpring.entities.Clientes;
import com.lipcam.PedidosApiSpring.exceptions.CustomMessageException;
import com.lipcam.PedidosApiSpring.exceptions.RegistroInexistenteException;
import com.lipcam.PedidosApiSpring.repositories.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
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

    public ClientesDTO findById(Long Id) {
        Clientes entity = _repository.findById(Id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        return new ClientesDTO(entity);
    }

    @Transactional
    public Clientes add(AddEditClienteRequestDTO addEditRequestDTO) {
        Clientes entity = _repository.findByCpf(addEditRequestDTO.getCpf());
        if (entity != null)
            throw new CustomMessageException("Cliente já existente com este CPF", HttpStatus.BAD_REQUEST);

        return _repository.save(new Clientes(addEditRequestDTO.getNome(), addEditRequestDTO.getCpf()));
    }

    @Transactional
    public void update(Long id, AddEditClienteRequestDTO addEditRequestDTO) {
        Clientes entity = _repository.findById(id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        entity.setNome(addEditRequestDTO.getNome());
        entity.setCpf(addEditRequestDTO.getCpf());
        _repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Clientes entity = _repository.findById(id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        _repository.delete(entity);
    }
}
