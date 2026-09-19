package com.gubartzen.demoendpoints1;

import java.util.List;

public interface IAcervo {
    List<Livro> todos();
    List<String> listaAutores();
    List<String> livrosAutorAno(String autor,int ano);
}

