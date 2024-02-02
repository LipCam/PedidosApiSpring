package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.Pedidos;
import com.lipcam.PedidosApiSpring.entities.PedidosItens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidosItensRepository extends JpaRepository<PedidosItens, Long> {

    @Query(value = "SELECT COALESCE(MAX(pi.itemId), 0) FROM PedidosItens pi WHERE pi.pedidoId = :idPedido")
    Integer findMaxIdItemByPedidoId(@Param("idPedido") Pedidos idPedido);

    @Query(value = "SELECT pi FROM PedidosItens pi WHERE pi.pedidoId = :idPedido AND pi.itemId = :idItem")
    PedidosItens findByPedidoIdAndItemId(@Param("idPedido") Pedidos idPedido, @Param("idItem") Integer idItem);

    @Query(value = "SELECT pi FROM PedidosItens pi WHERE pi.pedidoId = :idPedido")
    List<PedidosItens> findByPedidoId(@Param("idPedido") Pedidos idPedido);

    @Modifying
    @Query(value = "DELETE FROM PedidosItens pi WHERE pi.pedidoId = :idPedido")
    void deleteByPedidoId(Pedidos idPedido);
}
