package com.lipcam.PedidosApiSpring.dtos.cliente;

import com.lipcam.PedidosApiSpring.entities.Cliente;
import lombok.Data;

@Data
public class ClienteDTO {
    String Nome;
    String Cpf;

    public  ClienteDTO(){

    }

    public ClienteDTO(Cliente entity) {
        Nome = entity.getNome();
        Cpf = entity.getCpf();
    }
}
