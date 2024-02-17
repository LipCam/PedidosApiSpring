package com.lipcam.PedidosApiSpring.dtos.pedidos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidosItensDTO {
    private Integer itemId;

    @NotNull(message = "Informe o Produto")
    private Long idProduto;

    @NotNull(message = "Informe a quantidade")
    @Min(value = 1, message = "Quantidade deve ser maior que 0")
    private Integer quantidade;
}
