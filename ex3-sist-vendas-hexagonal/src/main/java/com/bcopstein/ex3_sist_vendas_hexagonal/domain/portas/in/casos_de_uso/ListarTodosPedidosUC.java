package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioPedidos;

@Component
public class ListarTodosPedidosUC {

	private final RepositorioPedidos repositorioPedidos;

	public ListarTodosPedidosUC(RepositorioPedidos repositorioPedidos) {
		this.repositorioPedidos = repositorioPedidos;
	}

    public List<Pedido> executar(){
        return repositorioPedidos.todosPedidos();
    }
}
