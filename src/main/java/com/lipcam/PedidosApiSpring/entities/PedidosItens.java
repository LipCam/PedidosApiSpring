package com.lipcam.PedidosApiSpring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@IdClass(PedidosItensPK.class)
public class PedidosItens {
    @Id
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedidoId;

    @Id
    private Integer itemId;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produtos;

    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public PedidosItens() {
    }

    public PedidosItens(Pedidos pedidoId, Integer itemId, Produtos produtos, Integer quantidade, BigDecimal valorUnitario, BigDecimal valorTotal) {
        this.pedidoId = pedidoId;
        this.itemId = itemId;
        this.produtos = produtos;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }
}
