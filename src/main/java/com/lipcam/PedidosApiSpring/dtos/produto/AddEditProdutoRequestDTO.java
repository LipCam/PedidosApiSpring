package com.lipcam.PedidosApiSpring.dtos.produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddEditProdutoRequestDTO {
    @NotBlank(message = "Campo Descrição deve ser preenchido")
    private String descricao;

    @NotNull(message = "Campo Preço deve ser preenchido")
    @Min(value = 1, message = "Campo Preço deve ser maior que 0")
    private BigDecimal preco;
}
