package com.lipcam.PedidosApiSpring.dtos.produto;

import com.lipcam.PedidosApiSpring.entities.Produtos;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutosDTO {
    String descricao;
    BigDecimal preco;

    public ProdutosDTO(){

    }

    public ProdutosDTO(Produtos entity) {
        descricao = entity.getDescricao();
        preco = entity.getPreco();
    }
}
