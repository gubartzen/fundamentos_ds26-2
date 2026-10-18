package com.bcopstein.ex3_sist_vendas_hexagonal.infra.adapters.in.rest;

import java.time.LocalDate;
import java.util.List;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ItemPedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.EstadoBrasil;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Pedido;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.StatusPedido;

public class PedidoResponseDTO {
    public static class ItemPedidoResponseDTO {
        private long produtoId;
        private int quantidade;
        private double precoProdutoConsiderado;

        public ItemPedidoResponseDTO() {
        }

        public ItemPedidoResponseDTO(long produtoId, int quantidade, double precoProdutoConsiderado) {
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

        public static ItemPedidoResponseDTO fromDomain(ItemPedido itemPedido) {
            return new ItemPedidoResponseDTO(
                itemPedido.getProdutoId(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoProdutoConsiderado()
            );
        }
    }

    private long id;
    private LocalDate data;
    private StatusPedido status;
    private EstadoBrasil estado;
    private double imposto;
    private double desconto;
    private double valorFinal;
    private List<ItemPedidoResponseDTO> itens;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(long id, LocalDate data, StatusPedido status, EstadoBrasil estado, double imposto,
            double desconto, double valorFinal, List<ItemPedidoResponseDTO> itens) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.estado = estado;
        this.imposto = imposto;
        this.desconto = desconto;
        this.valorFinal = valorFinal;
        this.itens = itens;
    }

    public long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public EstadoBrasil getEstado() {
        return estado;
    }

    public double getImposto() {
        return imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }

    public static PedidoResponseDTO fromDomain(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = pedido.getItens()
            .map(ItemPedidoResponseDTO::fromDomain)
            .toList();

        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getData(),
            pedido.getStatus(),
            pedido.getEstado(),
            pedido.getImposto(),
            pedido.getDesconto(),
            pedido.getValorFinal(),
            itens
        );
    }
}