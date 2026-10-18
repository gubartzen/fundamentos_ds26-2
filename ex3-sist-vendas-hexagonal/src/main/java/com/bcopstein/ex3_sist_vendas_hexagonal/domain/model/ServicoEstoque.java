package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios.RepositorioProdutos;

// Estoque simulado: qualquer item consultado começa sempre com 10 unidades
// Estoque simulado: armazena os produtos reservados associados ao pedido
@Service
public class ServicoEstoque {
    // Armazena id do produto e quantidade disponivel
    private Map<Long, Integer> estoque;
    // Armazena id do Pedido associado ao Pedido
    private Map<Long,Pedido> reservados;
    // Repositorio de produtos
    RepositorioProdutos repositorioProdutos;

    @Autowired
    public ServicoEstoque(RepositorioProdutos repositorioProdutos) {
        estoque = new HashMap<>();
        reservados = new HashMap<>();
        this.repositorioProdutos = repositorioProdutos;
    }

    // Necessario porque o Servico de estoque é simulado
    // Garante a sincronização com o banco de produtos que não 
    // é simulado
    private void sincronizaProdutosComEstoque() {
        List<Produto> produtos = repositorioProdutos.recuperaTodos();
        produtos.forEach(produto -> estoque.putIfAbsent(produto.getId(), 10));
    }


    public int consultarDisponibilidadePorProdutoId(long produtoId) {
        sincronizaProdutosComEstoque();
        return estoque.getOrDefault(produtoId, 0);
    }

    public void baixarEstoquePorProdutoId(long produtoId, int quantidade) {
        int qtdadeDisponivel = estoque.getOrDefault(produtoId, 0);
        if (qtdadeDisponivel < quantidade) throw new IllegalArgumentException("Quantidade indisponivel no estoque");
        estoque.put(produtoId,(qtdadeDisponivel - quantidade));
    }

    public void reporEstoquePorProdutoId(long produtoId, int quantidade) {
        int qtdadeDisponivel = estoque.getOrDefault(produtoId, 0);
        estoque.put(produtoId,(qtdadeDisponivel + quantidade));
    }

    public boolean reservaPedido(Pedido pedido){
        sincronizaProdutosComEstoque();
        // Se já está na lista de pedidos reservados gera exceção
        if (reservados.keySet().contains(pedido.getId())){
            throw new IllegalStateException("Pedido já reservado!");
        }
        // Verifica disponibilidade no estoque
        boolean todosDisponiveis = pedido.getItens().
            allMatch(itemPedido -> consultarDisponibilidadePorProdutoId(itemPedido.getProdutoId()) >= itemPedido.getQuantidade());
        if (!todosDisponiveis){
            return false;
        }
        // Da baixa no estoque de todos os que serão reservados
        pedido.getItens().forEach(itemPedido->baixarEstoquePorProdutoId(itemPedido.getProdutoId(), itemPedido.getQuantidade()));
        // Insere o pedido na lista de reservados
        reservados.put(pedido.getId(),pedido);
        return true;
    }

    public void liberarReservas(Pedido pedido){
        // Retorna as quantidades para o estoque
        pedido.getItens().forEach(itemPedido->reporEstoquePorProdutoId(itemPedido.getProdutoId(), itemPedido.getQuantidade()));
        // Retira o pedido da lista de reservados
        reservados.remove(pedido.getId());
    }

    public List<Produto> produtosDisponiveis(){
        sincronizaProdutosComEstoque();
        List<Produto> produtos = estoque.entrySet().stream()
            .filter(entry -> entry.getValue() > 0)
            .map(entry -> repositorioProdutos.recuperaProduto(entry.getKey()))
            .toList();
        return produtos;
    }
}
