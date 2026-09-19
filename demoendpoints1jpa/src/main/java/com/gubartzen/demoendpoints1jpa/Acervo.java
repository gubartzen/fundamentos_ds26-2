package com.gubartzen.demoendpoints1jpa;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;



@Component
public class Acervo implements IAcervo {

    private final LivroRepository livroRepository;

    @Autowired
    public Acervo(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @PostConstruct
    public void init() {
        // Inicializa com dados padrão se o banco estiver vazio
        if (livroRepository.count() == 0) {
            livroRepository.save(new Livro(10l, "Introdução ao Java", new Autor("Huguinho Pato"), 2022));
            // ...
            livroRepository.save(new Livro(25l, "Usando JPA", new Autor("Lala Pato"), 2026));
        }
    }

    public List<Livro> getAll() {
        return livroRepository.findAll();
    }

    public List<String> getTitulos() {
        return getAll().stream()
                .map(livro -> livro.getTitulo())
                .toList();
    }

    public List<Livro> getLivrosDoAutor(String autor) {
        return livroRepository.findByAutor(autor);
    }

    public boolean cadastraLivroNovo(Livro livro) {
        livroRepository.save(livro);
        return true;
    }

    public boolean removeLivro(long codigo) {
        if (livroRepository.existsById(codigo)) {
            livroRepository.deleteById(codigo);
            return true;
        }
        return false;
    }

    @Override
    public List<Livro> todos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'todos'");
    }

    @Override
    public List<String> listaAutores() {
        // TODO Auto-generated method stub
        return getAll().stream()
                .map(livro -> livro.getAutor().getNome())
                .toList();
    }

    @Override
    public List<String> livrosAutorAno(String autor, int ano) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'livrosAutorAno'");
    }
}
