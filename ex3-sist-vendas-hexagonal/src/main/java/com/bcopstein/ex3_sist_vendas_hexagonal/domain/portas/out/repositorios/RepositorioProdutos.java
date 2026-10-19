package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.out.repositorios;

import com.bcopstein.ex3_sist_vendas_hexagonal.domain.model.Produto;
import java.util.List;

public interface RepositorioProdutos {
    Produto recuperaProduto(long id);
    Produto persiste(Produto produto);
    List<Produto> recuperaTodos();
}
