package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Pedido {
    private final long id;
    private final LocalDate data;
    private StatusPedido status;
    private EstadoBrasil estado;
    private double imposto;
    private double desconto;
    private double valorFinal;
    private final List<ItemPedido> itens;

    public Pedido(long id, LocalDate data, EstadoBrasil estado){
        this.id = id;
        this.data = data;
        this.status = StatusPedido.NOVO;
        this.estado = estado;
        this.imposto = 0.0;
        this.desconto = 0.0;
        this.itens = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public EstadoBrasil getEstado(){
        return estado;
    }

    public LocalDate getData() {
        return data;
    }

    public Stream<ItemPedido> getItens(){
        return itens.stream();
    }

    public void defineStatus(StatusPedido status) {
        switch(status){
            case NOVO : 
                break;
            case RESERVADO : 
                if (this.status != StatusPedido.NOVO) throw new IllegalStateException("Só é possível reservar pedidos novos");
                break;
            case CANCELADO :
                break;
            case CONFIRMADO : 
                if (this.status != StatusPedido.RESERVADO) throw new IllegalStateException("Só é possível aprovar pedidos reservados");
                break;
        }
        this.status = status;
    }

    public void acrescentarItem(ItemPedido item) {
        itens.add(item);
    }

    public double getImposto() {
        return imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorFinal(){
        return valorFinal;
    }

    public int getQtdadeItens(){
        return itens.size();
    }

    public double somatorio(){
        return itens.stream()
               .mapToDouble(ItemPedido::calculaValorItem)
               .sum();
    }

    public void calculaCusto(double imposto, double desconto) {
        this.desconto = desconto;
        this.imposto = imposto;
        this.valorFinal = somatorio() + imposto - desconto;
    }
}
