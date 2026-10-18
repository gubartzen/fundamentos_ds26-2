package com.bcopstein.ex3_sist_vendas_hexagonal.infra.adapters.in.rest;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Produto;

public class ProdutoResponseDTO {
    private long id;
    private String descricao;
    private double preco;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(long id, String descricao, double preco) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public static ProdutoResponseDTO fromDomain(Produto produto) {
        return new ProdutoResponseDTO(produto.getId(), produto.getDescricao(), produto.getPreco());
    }

    public Produto toDomain() {
        return new Produto(this.id, this.descricao, this.preco);
    }
}
