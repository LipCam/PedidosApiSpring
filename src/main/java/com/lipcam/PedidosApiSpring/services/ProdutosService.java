package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.AddEditProdutoRequestDTO;
import com.lipcam.PedidosApiSpring.dtos.produto.ProdutoDTO;
import com.lipcam.PedidosApiSpring.entities.Produto;
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

    public List<Produto> findAll(Produto entity)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(entity, exampleMatcher);

        return _repository.findAll(example);
    }

    public ProdutoDTO findById(Integer Id)
    {
        Produto entity =_repository.findById(Id).orElse(null);
        if(entity != null)
            return new ProdutoDTO(entity);
        return null;
    }

    @Transactional
    public ResponseEntity add(AddEditProdutoRequestDTO addEditRequestDTO) {
        Produto entity = _repository.findByDescricao(addEditRequestDTO.getDescricao());
        if (entity != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Produto já existente com esta descrição"));

        entity = _repository.save(new Produto(addEditRequestDTO.getDescricao(), addEditRequestDTO.getPreco()));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @Transactional
    public ResponseDTO update(Integer id, AddEditProdutoRequestDTO addEditRequestDTO)
    {
        Produto entity = _repository.findById(id).orElse(null);
        if(entity != null)
        {
            entity.setDescricao(addEditRequestDTO.getDescricao());
            entity.setPreco(addEditRequestDTO.getPreco());
            _repository.save(entity);
            return new ResponseDTO("OK", "Edição realizada com sucesso");
        }
        return new ResponseDTO("Erro", "Registro inexistente");
    }

    @Transactional
    public ResponseDTO delete(Integer id) {
        Produto entity = _repository.findById(id).orElse(null);

        if(entity != null) {
            _repository.delete(entity);
            return new ResponseDTO("OK", "Exclusão realizada com sucesso");
        }
        return new ResponseDTO("Erro", "Registro inexistente");
    }
}
