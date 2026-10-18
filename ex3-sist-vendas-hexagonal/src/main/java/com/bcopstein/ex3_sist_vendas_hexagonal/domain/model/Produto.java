package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

public class Produto {
    private final long id;
    private final String descricao;
    private final double preco;

    public Produto(long id, String descricao, double preco) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto{");
        sb.append("id=").append(id);
        sb.append(", descricao=").append(descricao);
        sb.append(", preco=").append(preco);
        sb.append('}');
        return sb.toString();
    }
}
