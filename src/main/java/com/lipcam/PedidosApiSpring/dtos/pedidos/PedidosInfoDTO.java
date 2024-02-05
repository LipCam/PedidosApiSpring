package com.lipcam.PedidosApiSpring.dtos.pedidos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PedidosInfoDTO {
    private Long id;
    private String nomeCliente;
    private String cpfCliente;
    private String dataPedido;
    private BigDecimal valorTotal;
    private List<PedidosItensInfoDTO> itens;
}
