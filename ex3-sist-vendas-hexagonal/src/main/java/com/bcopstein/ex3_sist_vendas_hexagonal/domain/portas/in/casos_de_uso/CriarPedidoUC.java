package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ItemPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ServicoDesconto;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ServicoImposto;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioPedidos;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioProdutos;

@Component
public class CriarPedidoUC {
    private final RepositorioPedidos repositorioPedidos;
    private final RepositorioProdutos repositorioProdutos;
    private final ServicoImposto servicoImposto;
    private final ServicoDesconto servicoDesconto;

    @Autowired
    public CriarPedidoUC(RepositorioPedidos repositorioPedidos, RepositorioProdutos repositorioProdutos,
                         ServicoImposto servicoImposto, ServicoDesconto servicoDesconto){
        this.repositorioPedidos = repositorioPedidos;
        this.repositorioProdutos = repositorioProdutos;
        this.servicoImposto = servicoImposto;
        this.servicoDesconto = servicoDesconto;
    }

    public Pedido executar(CriarPedidoCommand pedidoCommand){
        List<ItemPedido> itens = pedidoCommand.itens().stream()
            .map(ipc -> new ItemPedido(ipc.produtoId(),
                                       ipc.quantidade(),
                                       repositorioProdutos.recuperaProduto(ipc.produtoId()).getPreco()))
            .toList();
            Pedido pedido = new Pedido(0,LocalDate.now(),pedidoCommand.estado());
            itens.stream()
                .forEach(item -> pedido.acrescentarItem(item));
        // Calcula impostos, descontos e o valor final
        double impostos = servicoImposto.calcularImposto(pedidoCommand.estado(), pedido);
        double descontos = servicoDesconto.calculaDesconto(pedido);
        pedido.calculaCusto(impostos,descontos);
        return repositorioPedidos.persiste(pedido);
    }
}
