package com.lipcam.PedidosApiSpring.dtos.produto;

import com.lipcam.PedidosApiSpring.entities.Produto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    String descricao;
    BigDecimal preco;

    public ProdutoDTO(){

    }

    public ProdutoDTO(Produto entity) {
        descricao = entity.getDescricao();
        preco = entity.getPreco();
    }
}
