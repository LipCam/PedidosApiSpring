package com.lipcam.PedidosApiSpring.dtos.pedidos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PedidosItensInfoDTO {
    private Integer itemId;
    private String descProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
}
