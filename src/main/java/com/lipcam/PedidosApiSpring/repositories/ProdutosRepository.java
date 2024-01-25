package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
    Produto findByDescricao(String descricao);
}
