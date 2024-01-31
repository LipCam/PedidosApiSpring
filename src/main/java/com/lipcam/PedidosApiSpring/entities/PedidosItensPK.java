package com.lipcam.PedidosApiSpring.entities;

import lombok.Data;

@Data
public class PedidosItensPK {
    private Pedidos pedidos;
    private Integer itemId;

    public PedidosItensPK(Pedidos pedidos, Integer itemId) {
        this.pedidos = pedidos;
        this.itemId = itemId;
    }

    public PedidosItensPK() {
    }
}
