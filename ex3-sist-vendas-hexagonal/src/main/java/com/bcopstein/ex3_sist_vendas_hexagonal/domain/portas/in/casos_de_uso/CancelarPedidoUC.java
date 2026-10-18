package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ServicoEstoque;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.StatusPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioPedidos;

@Component
public class CancelarPedidoUC {
	private final ServicoEstoque servicoEstoque;
	private final RepositorioPedidos repositorioPedidos;

	@Autowired
	public CancelarPedidoUC(ServicoEstoque servicoEstoque, RepositorioPedidos repositorioPedidos) {
		this.servicoEstoque = servicoEstoque;
		this.repositorioPedidos = repositorioPedidos;
	}

    public Pedido executar(Long pedidoId){
        Pedido pedido = repositorioPedidos.recuperaPedido(pedidoId);
        if (pedido.getStatus() == StatusPedido.RESERVADO){
            servicoEstoque.liberarReservas(pedido);
        }
        pedido.defineStatus(StatusPedido.CANCELADO);
        return repositorioPedidos.persiste(pedido);
    }
}
