package com.bcopstein.ex3_sist_vendas_hexagonal.infra.adapters.in.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.EstadoBrasil;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Produto;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.StatusPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.CancelarPedidoUC;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.ConfirmarPedidoUC;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.CriarPedidoCommand;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.CriarPedidoUC;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.ItemCommand;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.ListarProdutosDisponiveisUC;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.ListarTodosPedidosUC;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso.ReservarPedidoUC;

@RestController
public class Controller {
    private final CriarPedidoUC criarPedidoUC;
    private final ListarProdutosDisponiveisUC listarProdutosDisponiveisUC;
    private final ReservarPedidoUC reservarPedidoUC;
    private final ConfirmarPedidoUC confirmarPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;
    private final ListarTodosPedidosUC listarTodosPedidosUC;

    @Autowired
    public Controller(CriarPedidoUC criarPedidoUC,
            ListarProdutosDisponiveisUC listarProdutosDisponiveisUC,
            ReservarPedidoUC reservarPedidoUC,
            ConfirmarPedidoUC confirmarPedidoUC,
            CancelarPedidoUC cancelarPedidoUC,
            ListarTodosPedidosUC listarTodosPedidosUC) {
        this.criarPedidoUC = criarPedidoUC;
        this.listarProdutosDisponiveisUC = listarProdutosDisponiveisUC;
        this.reservarPedidoUC = reservarPedidoUC;
        this.confirmarPedidoUC = confirmarPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.listarTodosPedidosUC = listarTodosPedidosUC;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String mensagemDeBoasVindas() {
        return "Bem vindo as lojas ACME";
    }

    @GetMapping("cancelarPedidosEfetivadosHaMaisDeUmaSemana")
    @CrossOrigin(origins = "*")
    public boolean cancelarPedidosEfetivadosHaMaisDeUmaSemana() {
        List<Pedido> pedidos = listarTodosPedidosUC.executar();
        LocalDate dataSemanaAnt = LocalDate.now().minusWeeks(1);
        pedidos.stream()
                .filter(pedido -> pedido.getStatus() == StatusPedido.RESERVADO)
                .filter(pedido -> pedido.getData().isBefore(dataSemanaAnt))
                .forEach(pedido -> cancelarPedidoUC.executar(pedido.getId()));
        return true;
    }


    @GetMapping("pedidosNaoEfetivadosHaMaisDeUmaSemana")
    @CrossOrigin(origins = "*")
    public List<PedidoResponseDTO> pedidosNaoEfetivadosHaMaisDeUmaSemana() {
        List<Pedido> pedidos = listarTodosPedidosUC.executar();
        LocalDate dataSemanaAnt = LocalDate.now().minusWeeks(1);
        return pedidos.stream()
                .filter(pedido -> pedido.getStatus() == StatusPedido.RESERVADO)
                .filter(pedido -> pedido.getData().isBefore(dataSemanaAnt) )
                .map(PedidoResponseDTO::fromDomain)
                .toList();
    }

    @GetMapping("pedidosNaoEfetivados")
    @CrossOrigin(origins = "*")
    public List<PedidoResponseDTO> pedidosNaoEfetivados() {
        List<Pedido> pedidos = listarTodosPedidosUC.executar();
        return pedidos.stream()
                .filter(pedido -> pedido.getStatus() == StatusPedido.RESERVADO)
                .map(PedidoResponseDTO::fromDomain)
                .toList();
    }

    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<ProdutoResponseDTO> produtosDisponiveis() {
        List<Produto> produtos = listarProdutosDisponiveisUC.executar();
        return produtos.stream()
                .map(produto -> ProdutoResponseDTO.fromDomain(produto))
                .toList();
    }

    @GetMapping("todosPedidos")
    @CrossOrigin(origins = "*")
    public List<PedidoResponseDTO> todosPedidos() {
        List<Pedido> pedidos = listarTodosPedidosUC.executar();
        return pedidos.stream()
                .map(PedidoResponseDTO::fromDomain)
                .toList();
    }

    @GetMapping("reservarPedido/id/{pedidoId}")
    @CrossOrigin(origins = "*")
    public PedidoResponseDTO reservarPedido(@PathVariable(value = "pedidoId") Long pedidoId) {
        Pedido pedido = reservarPedidoUC.executar(pedidoId);
        return PedidoResponseDTO.fromDomain(pedido);
    }

    @GetMapping("confirmarPedido/id/{pedidoId}")
    @CrossOrigin(origins = "*")
    public PedidoResponseDTO confirmarPedido(@PathVariable(value = "pedidoId") Long pedidoId) {
        Pedido pedido = confirmarPedidoUC.executar(pedidoId);
        return PedidoResponseDTO.fromDomain(pedido);
    }

    @GetMapping("cancelarPedido/id/{pedidoId}")
    @CrossOrigin(origins = "*")
    public PedidoResponseDTO cancelarPedido(@PathVariable(value = "pedidoId") Long pedidoId) {
        Pedido pedido = cancelarPedidoUC.executar(pedidoId);
        return PedidoResponseDTO.fromDomain(pedido);
    }

    @PostMapping("criarPedido/estado/{estado}")
    @CrossOrigin(origins = "*")
    public PedidoResponseDTO criarPedido(@PathVariable(value = "estado") EstadoBrasil estado,
            @RequestBody final List<ItemPedidoRequestDTO> pedidoRequest) {
        // Cria a lista de ItemCommand
        List<ItemCommand> itens = pedidoRequest.stream()
                .map(pr -> new ItemCommand(pr.getId(), pr.getQuantidade()))
                .toList();
        // Cria CriarPedidoCommand
        CriarPedidoCommand cpc = new CriarPedidoCommand(itens, estado);
        Pedido pedido = criarPedidoUC.executar(cpc);
        return PedidoResponseDTO.fromDomain(pedido);
    }
}
