package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Produto;
import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.ServicoEstoque;

@Component
public class ListarProdutosDisponiveisUC {
    private final ServicoEstoque servicoEstoque;

    @Autowired
    public ListarProdutosDisponiveisUC(ServicoEstoque servicoEstoque){
        this.servicoEstoque = servicoEstoque;
    }

    public List<Produto> executar(){
        return servicoEstoque.produtosDisponiveis();
    }
}
