package com.lipcam.PedidosApiSpring.repositories;

import com.lipcam.PedidosApiSpring.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCpf(String cpf);
}
