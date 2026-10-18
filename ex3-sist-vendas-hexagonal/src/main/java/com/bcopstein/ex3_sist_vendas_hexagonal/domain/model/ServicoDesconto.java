package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

import org.springframework.stereotype.Component;

@Component
public class ServicoDesconto {
    public double calculaDesconto(Pedido pedido) {
        return (pedido.getQtdadeItens() < 3) ? 0.0 : pedido.somatorio() * 0.05;
    }
}
