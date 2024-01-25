package com.lipcam.PedidosApiSpring.dtos.produto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddEditProdutoRequestDTO {
    private String descricao;
    private BigDecimal preco;
}
