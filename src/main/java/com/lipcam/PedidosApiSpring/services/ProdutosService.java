package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.AddEditProdutoRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.ProdutosDTO;
import com.lipcam.PedidosApiSpring.entities.Produtos;
import com.lipcam.PedidosApiSpring.exceptions.CustomMessageException;
import com.lipcam.PedidosApiSpring.exceptions.RegistroInexistenteException;
import com.lipcam.PedidosApiSpring.repositories.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutosService {

    @Autowired
    ProdutosRepository _repository;

    public List<Produtos> findAll(Produtos entity)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(entity, exampleMatcher);

        return _repository.findAll(example);
    }

    public ProdutosDTO findById(Long Id) {
        Produtos entity = _repository.findById(Id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        return new ProdutosDTO(entity);
    }

    @Transactional
    public Produtos add(AddEditProdutoRequestDTO addEditRequestDTO) {
        Produtos entity = _repository.findByDescricao(addEditRequestDTO.getDescricao());
        if (entity != null)
            throw new CustomMessageException("Produto já existente com esta descrição", HttpStatus.BAD_REQUEST);

        return _repository.save(new Produtos(addEditRequestDTO.getDescricao(), addEditRequestDTO.getPreco()));
    }

    @Transactional
    public void update(Long id, AddEditProdutoRequestDTO addEditRequestDTO) {
        Produtos entity = _repository.findById(id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        entity.setDescricao(addEditRequestDTO.getDescricao());
        entity.setPreco(addEditRequestDTO.getPreco());
        _repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Produtos entity = _repository.findById(id).orElse(null);
        if (entity == null)
            throw new RegistroInexistenteException();

        _repository.delete(entity);
    }
}
