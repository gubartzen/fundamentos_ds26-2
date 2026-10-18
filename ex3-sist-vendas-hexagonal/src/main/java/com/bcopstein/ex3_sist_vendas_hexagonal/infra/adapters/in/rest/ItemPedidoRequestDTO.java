package com.bcopstein.ex3_sist_vendas_hexagonal.infra.adapters.in.rest;

import java.util.List;
import java.util.stream.Stream;

public class ItemPedidoRequestDTO {
    private long id;
    private int quantidade;

    public ItemPedidoRequestDTO(){

    }
    
    public ItemPedidoRequestDTO(long id, int quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
