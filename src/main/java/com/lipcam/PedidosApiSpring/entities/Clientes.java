package com.lipcam.PedidosApiSpring.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String nome;

    @Column(length = 11)
    String cpf;

    public Clientes() {

    }

    public Clientes(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}
