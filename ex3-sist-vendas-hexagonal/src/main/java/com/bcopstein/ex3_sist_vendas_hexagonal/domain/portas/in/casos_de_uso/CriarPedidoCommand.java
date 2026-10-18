package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import java.util.List;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.EstadoBrasil;

// Necessário porque os itens da requisição de pedido 
// não contém o preço do produto, apenas codigo e quantidade
public record CriarPedidoCommand(List<ItemCommand> itens,EstadoBrasil estado) {

}
