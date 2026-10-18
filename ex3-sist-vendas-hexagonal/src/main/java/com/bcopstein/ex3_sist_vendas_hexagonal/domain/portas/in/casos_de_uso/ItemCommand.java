package com.bcopstein.ex3_sist_vendas_hexagonal.domain.portas.in.casos_de_uso;

public record ItemCommand(Long produtoId, int quantidade) {
}
