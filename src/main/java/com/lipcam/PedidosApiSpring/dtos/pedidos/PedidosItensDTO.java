package com.lipcam.PedidosApiSpring.dtos.pedidos;

import lombok.Data;

@Data
public class PedidosItensDTO {
    private  Integer idProduto;
    private Integer quantidade;
}
