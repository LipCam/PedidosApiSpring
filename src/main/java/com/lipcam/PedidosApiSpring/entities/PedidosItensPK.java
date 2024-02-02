package com.lipcam.PedidosApiSpring.entities;

import lombok.Data;

@Data
public class PedidosItensPK {
    private Pedidos pedidoId;
    private Integer itemId;

    public PedidosItensPK(Pedidos pedidoId, Integer itemId) {
        this.pedidoId = pedidoId;
        this.itemId = itemId;
    }

    public PedidosItensPK() {
    }
}
