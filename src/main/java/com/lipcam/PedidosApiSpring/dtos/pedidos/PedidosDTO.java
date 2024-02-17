package com.lipcam.PedidosApiSpring.dtos.pedidos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidosDTO {
    private Long idPedido;

    @NotNull(message = "Informe o Cliente")
    private Long idCliente;

    private List<PedidosItensDTO> itens;
}
