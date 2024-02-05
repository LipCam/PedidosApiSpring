package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.Clientes;
import com.lipcam.PedidosApiSpring.entities.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    List<Pedidos> findByClientes(Clientes clientes);

    @Query("SELECT p from Pedidos p " +
            "left join fetch p.itens " +
            "where p.id = :id")
     Pedidos findByIdFetchItens(@Param("id") Long id);
}
