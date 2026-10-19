package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios;

import java.util.List;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;

public interface RepositorioPedidos {
    Pedido persiste(Pedido pedido);
    Pedido recuperaPedido(long id);
    List<Pedido> todosPedidos();
}
