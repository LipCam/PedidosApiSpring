package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosItensDTO;
import com.lipcam.PedidosApiSpring.entities.Clientes;
import com.lipcam.PedidosApiSpring.entities.Pedidos;
import com.lipcam.PedidosApiSpring.entities.PedidosItens;
import com.lipcam.PedidosApiSpring.entities.Produtos;
import com.lipcam.PedidosApiSpring.repositories.ClientesRepository;
import com.lipcam.PedidosApiSpring.repositories.PedidosItensRepository;
import com.lipcam.PedidosApiSpring.repositories.PedidosRepository;
import com.lipcam.PedidosApiSpring.repositories.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidosService {

    @Autowired
    PedidosRepository _pedidosRepository;

    @Autowired
    PedidosItensRepository _pedidosItensRepository;

    @Autowired
    ClientesRepository _clientesRepository;

    @Autowired
    ProdutosRepository _produtosRepository;

    @Transactional
    public ResponseEntity addPedido(PedidosDTO pedidosDTO) {
        Clientes clientes = _clientesRepository.findById(pedidosDTO.getIdCliente()).orElse(null);
        if (clientes == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Erro", "Cliente inexistente"));

        Pedidos pedidos = new Pedidos();
        pedidos.setClientes(clientes);
        pedidos.setDataPedido(LocalDate.now());

        List<PedidosItens> itemsPedido = converterItens(pedidos, pedidosDTO.getItens());
        pedidos.setTotal(getSumTotalItens(itemsPedido));
        _pedidosRepository.save(pedidos);
        _pedidosItensRepository.saveAll(itemsPedido);
        pedidos.setItens(itemsPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("OK", "Pedido salvo com sucesso"));
    }

    private List<PedidosItens> converterItens(Pedidos pedidos, List<PedidosItensDTO> lstPedidosItensDTOS) {
        List<PedidosItens> lst = new ArrayList<>();

        lstPedidosItensDTOS.forEach(item -> {
            Integer idProduto = item.getIdProduto();
            Produtos produtos = _produtosRepository
                    .findById(idProduto)
                    .orElseThrow(
                            () -> new RuntimeException("Código de produto inválido: " + idProduto)
                    );

            PedidosItens pedidosItens = new PedidosItens();
            pedidosItens.setPedidos(pedidos);
            pedidosItens.setItemId(lstPedidosItensDTOS.indexOf(item) + 1);
            pedidosItens.setProdutos(produtos);
            pedidosItens.setQuantidade(item.getQuantidade());
            pedidosItens.setValorUnitario(produtos.getPreco());
            pedidosItens.setValorTotal(new BigDecimal(item.getQuantidade()).multiply(produtos.getPreco()));
            lst.add(pedidosItens);
        });
        return lst;
    }

    private BigDecimal getSumTotalItens(List<PedidosItens> itemsPedido){
        return itemsPedido.stream().map(
                itens -> {
                    return new BigDecimal(itens.getQuantidade()).multiply(itens.getValorUnitario());
                }
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    //Neste caso o incremento do idItem é automatico na base
//    private List<PedidoItens> converterItens(Pedido pedido, List<PedidosItensDTO> lstPedidosItensDTOS){
//        return lstPedidosItensDTOS
//                .stream()
//                .map( dto -> {
//                    Integer idProduto = dto.getIdProduto();
//                    Produto produto = _produtosRepository
//                            .findById(idProduto)
//                            .orElseThrow(
//                                    () -> new RuntimeException("Código de produto inválido: "+ idProduto)
//                            );
//
//                    PedidoItens pedidoItens = new PedidoItens();
//                    pedidoItens.setQuantidade(dto.getQuantidade());
//                    pedidoItens.setPedido(pedido);
//                    pedidoItens.setProduto(produto);
//                    return pedidoItens;
//                }).collect(Collectors.toList());
//    }
}
