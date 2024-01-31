package com.lipcam.PedidosApiSpring.dtos.cliente;

import com.lipcam.PedidosApiSpring.entities.Clientes;
import lombok.Data;

@Data
public class ClientesDTO {
    String Nome;
    String Cpf;

    public ClientesDTO(){

    }

    public ClientesDTO(Clientes entity) {
        Nome = entity.getNome();
        Cpf = entity.getCpf();
    }
}
