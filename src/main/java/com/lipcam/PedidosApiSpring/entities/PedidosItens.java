package com.lipcam.PedidosApiSpring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@IdClass(PedidosItensPK.class)
public class PedidosItens {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "pedido_id")
//    private Pedido pedido;

    @Id
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedidos;

    @Id
    private Integer itemId;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produtos;

    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
}
