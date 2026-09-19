package com.gubartzen.demoendpoints1;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@Primary
public class Acervo implements IAcervo {

    private List<Livro> livros = new LinkedList<>();

    @Autowired
    private LivrosRepository LivrosRepository;

    @Autowired
    private EstatisticaAutor estAutor;

    @Override
    public List<Livro> todos() {
        this.estAutor = estAutor;

        for (Livro livro: this.livros) {
            estAutor.adicionaAutor(livro.getAutor());
        }

        return this.livros;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    @Override
    public List<String> listaAutores() {
        // TODO Auto-generated method stub
        List<String> autores = new LinkedList<>();
        for (Livro livro: livros) {
            autores.add(livro.getAutor());
        }

        return autores;
    }

    public boolean cadastraLivro(Livro livro) {
        for (Livro book: livros) {
            if (book.getTitulo().equals(livro.getTitulo()))
                return false;
        }

        estAutor.adicionaAutor(livro.getAutor());
        livros.add(livro);
        LivrosRepository.save(livro);
        return true;
    }

    @Override
    public List<String> livrosAutorAno(String autor, int ano) {
        // TODO Auto-generated method stub
        List<String> retorno = new LinkedList<>();
        for (Livro livro : livros) {
            if (livro.getAno() == ano && livro.getAutor().equals(autor)) {
                retorno.add(livro.toString());
            }
        }
        return retorno;
    }

    @PostConstruct
    public void init() {
    // Inicializa com dados padrão se o banco estiver vazio
    if (LivrosRepository.count() == 0) {
        LivrosRepository.save(new Livro(10l, "Introdução ao Java", "Huguinho Pato", 2022));
        LivrosRepository.save(new Livro(20l, "Introdução ao Spring-Boot", "Zezinho Pato", 2020));
        LivrosRepository.save(new Livro(15l, "Principios SOLID", "Luizinho Pato", 2023));
        LivrosRepository.save(new Livro(17l, "Padroes de Projeto", "Lala Pato", 2019));
        LivrosRepository.save(new Livro(25l, "Usando JPA", "Lala Pato", 2026));
    }
    }

}
