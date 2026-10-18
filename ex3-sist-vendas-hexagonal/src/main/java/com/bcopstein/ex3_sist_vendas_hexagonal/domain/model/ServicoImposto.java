package com.bcopstein.ex3_sist_vendas_hexagonal.domain.model;

import org.springframework.stereotype.Component;

@Component
public class ServicoImposto {
    public double calcularImposto(EstadoBrasil estado, Pedido pedido) {
        return switch(estado){
            case RS -> pedido.somatorio() * 0.1;
            case SC -> pedido.somatorio() * 0.12;
            case PR -> (pedido.getQtdadeItens() > 10) ? pedido.somatorio()*0.8:pedido.somatorio()*0.15;
            default -> pedido.somatorio()*0.8;
        };
    }
}
