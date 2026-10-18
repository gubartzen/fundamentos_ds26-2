package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ServicoEstoque;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.StatusPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioPedidos;

@Component
public class ReservarPedidoUC {
	private final ServicoEstoque servicoEstoque;
	private final RepositorioPedidos repositorioPedidos;

	@Autowired
	public ReservarPedidoUC(ServicoEstoque servicoEstoque, RepositorioPedidos repositorioPedidos) {
		this.servicoEstoque = servicoEstoque;
		this.repositorioPedidos = repositorioPedidos;
    }

    public Pedido executar(long pedidoId){
        Pedido pedido = repositorioPedidos.recuperaPedido(pedidoId);
        if (pedido.getStatus() != StatusPedido.NOVO){
            throw new IllegalStateException("So pode reservar pedido novo");
        }
        if (servicoEstoque.reservaPedido(pedido)){
            pedido.defineStatus(StatusPedido.RESERVADO);
            return repositorioPedidos.persiste(pedido);
        }else{
            return pedido;
        }
    }
}
