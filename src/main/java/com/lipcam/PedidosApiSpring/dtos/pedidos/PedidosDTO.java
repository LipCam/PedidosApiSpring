package com.lipcam.PedidosApiSpring.dtos.pedidos;

import lombok.Data;

import java.util.List;

@Data
public class PedidosDTO {
    private Long idPedido;
    private Long idCliente;
    private List<PedidosItensDTO> itens;
}
