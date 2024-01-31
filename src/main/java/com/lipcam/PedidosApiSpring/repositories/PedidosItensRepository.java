package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.PedidosItens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidosItensRepository extends JpaRepository<PedidosItens, Integer> {

}
