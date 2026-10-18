package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

public class ItemPedido {
    private final long produtoId;
    private final int quantidade;
    private final double precoProdutoConsiderado;

    public ItemPedido(long produtoId, int quantidade, double precoProdutoConsiderado) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoProdutoConsiderado = precoProdutoConsiderado;
    }

    public long getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoProdutoConsiderado() {
        return precoProdutoConsiderado;
    }

    public double calculaValorItem() {
        return quantidade * precoProdutoConsiderado;
    }
}
