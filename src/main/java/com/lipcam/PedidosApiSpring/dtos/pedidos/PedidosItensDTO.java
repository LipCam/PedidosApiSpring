package com.lipcam.PedidosApiSpring.dtos.pedidos;

import lombok.Data;

@Data
public class PedidosItensDTO {
    private Integer itemId;
    private Long idProduto;
    private Integer quantidade;
}
