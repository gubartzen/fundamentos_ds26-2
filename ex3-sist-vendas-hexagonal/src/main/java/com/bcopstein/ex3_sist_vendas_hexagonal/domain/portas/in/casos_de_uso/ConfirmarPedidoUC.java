package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.StatusPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioPedidos;

@Component
public class ConfirmarPedidoUC {
	private final RepositorioPedidos repositorioPedidos;

	@Autowired
	public ConfirmarPedidoUC(RepositorioPedidos repositorioPedidos) {
		this.repositorioPedidos = repositorioPedidos;
	}

    public Pedido executar(Long pedidoId){
        Pedido pedido = repositorioPedidos.recuperaPedido(pedidoId);
        if (pedido.getStatus() != StatusPedido.RESERVADO){
            throw new IllegalArgumentException("Só pode confirmar pedidos reservados");
        }
        pedido.defineStatus(StatusPedido.CONFIRMADO);
        return repositorioPedidos.persiste(pedido);
    }
}

