package com.lipcam.PedidosApiSpring.services;

import com.lipcam.PedidosApiSpring.dtos.ResponseDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosInfoDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosItensDTO;
import com.lipcam.PedidosApiSpring.dtos.pedidos.PedidosItensInfoDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
        _pedidosRepository.save(pedidos);
        _pedidosItensRepository.saveAll(itemsPedido);
        pedidos.setItens(itemsPedido);

        atualizaTotalPedido(pedidos);

        pedidosDTO.setIdPedido(pedidos.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosDTO);
        //return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("OK", "Pedido salvo com sucesso"));
    }

    private void atualizaTotalPedido(Pedidos pedidos) {
        List<PedidosItens> itemsPedido = _pedidosItensRepository.findByPedidoId(pedidos);
        pedidos.setTotal(getSumTotalItens(itemsPedido));
        _pedidosRepository.save(pedidos);
    }

    private BigDecimal getSumTotalItens(List<PedidosItens> itemsPedido){
        return itemsPedido.stream().map(
                itens -> {
                    return new BigDecimal(itens.getQuantidade()).multiply(itens.getValorUnitario());
                }
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<PedidosItens> converterItens(Pedidos pedidos, List<PedidosItensDTO> lstPedidosItensDTOS) {
        List<PedidosItens> lst = new ArrayList<>();

        lstPedidosItensDTOS.forEach(item -> {
            Long idProduto = item.getIdProduto();
            Produtos produtos = _produtosRepository
                    .findById(idProduto)
                    .orElseThrow(
                            () -> new RuntimeException("Código de produto inválido: " + idProduto)
                    );

            PedidosItens pedidosItens = new PedidosItens(
                    pedidos,
                    lstPedidosItensDTOS.indexOf(item) + 1,
                    produtos,
                    item.getQuantidade(),
                    produtos.getPreco(),
                    new BigDecimal(item.getQuantidade()).multiply(produtos.getPreco())
            );
            lst.add(pedidosItens);
        });
        return lst;
    }


    @Transactional
    public ResponseDTO deletePedido(Long idPedido){
        Pedidos pedidos = _pedidosRepository.findById(idPedido).orElse(null);
        if (pedidos == null)
            return new ResponseDTO("Erro", "Pedido inexistente");

        _pedidosItensRepository.deleteByPedidoId(pedidos);
        _pedidosRepository.delete(pedidos);

        return new ResponseDTO("OK", "Exclusão realizada com sucesso");
    }

    @Transactional
    public ResponseDTO addItemPedido(Long id, PedidosItensDTO pedidosItensDTO) {
        Pedidos pedidos = _pedidosRepository.findById(id).orElse(null);
        if (pedidos == null)
            return new ResponseDTO("Erro", "Pedido inexistente");

        Produtos produtos = _produtosRepository.findById(pedidosItensDTO.getIdProduto()).orElse(null);
        if (produtos == null)
            return new ResponseDTO("Erro", "Produto inexistente");

        Integer idItem = _pedidosItensRepository.findMaxIdItemByPedidoId(pedidos) + 1;
        PedidosItens pedidosItens = new PedidosItens(
                pedidos,
                idItem,
                produtos,
                pedidosItensDTO.getQuantidade(),
                produtos.getPreco(),
                new BigDecimal(pedidosItensDTO.getQuantidade()).multiply(produtos.getPreco())
        );
        _pedidosItensRepository.save(pedidosItens);

        atualizaTotalPedido(pedidos);

        return new ResponseDTO("OK", "Item salvo com sucesso");
    }

    @Transactional
    public ResponseDTO deleteItemPedido(Long idPedido, Integer idItem){
        Pedidos pedidos = _pedidosRepository.findById(idPedido).orElse(null);
        if (pedidos == null)
            return new ResponseDTO("Erro", "Pedido inexistente");

        PedidosItens pedidosItens = _pedidosItensRepository.findByPedidoIdAndItemId(pedidos, idItem);
        if (pedidosItens == null)
            return new ResponseDTO("Erro", "Item inexistente");

        _pedidosItensRepository.delete(pedidosItens);

        atualizaTotalPedido(pedidos);

        return new ResponseDTO("OK", "Exclusão realizada com sucesso");
    }

    public PedidosInfoDTO getPedidoById(Long idPedido) {
        Pedidos pedidos = _pedidosRepository.findByIdFetchItens(idPedido);
        if (pedidos == null)
            throw  new RuntimeException("Pedido inexistente");

        return convertPedidosInfoDTO(pedidos);
    }

    private PedidosInfoDTO convertPedidosInfoDTO(Pedidos pedidos){
        return PedidosInfoDTO.builder().id(pedidos.getId())
                .nomeCliente(pedidos.getClientes().getNome())
                .cpfCliente(pedidos.getClientes().getCpf())
                .dataPedido(pedidos.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .valorTotal(pedidos.getTotal())
                .itens(convertPedidosItensInfoDTO(pedidos.getItens()))
                .build();
    }

    private List<PedidosItensInfoDTO> convertPedidosItensInfoDTO(List<PedidosItens> lstPedidosItens) {
        if (lstPedidosItens.isEmpty())
            return Collections.emptyList();

        return lstPedidosItens.stream().map(item -> {
            return PedidosItensInfoDTO.builder().itemId(item.getItemId())
                    .descProduto(item.getProdutos().getDescricao())
                    .quantidade(item.getQuantidade())
                    .valorUnitario(item.getValorUnitario())
                    .valorTotal(item.getValorTotal())
                    .build();
        }).toList();
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
