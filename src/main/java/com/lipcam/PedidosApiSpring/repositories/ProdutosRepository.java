package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    Produtos findByDescricao(String descricao);
}
