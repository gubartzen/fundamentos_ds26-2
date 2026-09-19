package com.gubartzen.demoendpoints1jpa;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/biblioteca")
public class DemoController {
    @Autowired
    Acervo acervo;

    @Autowired
    EstatisticaAutor estAutor;

    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public String getSaudacao() {
        return "Bem vindo a biblioteca central!";
    }

    @GetMapping("/livros")
    @CrossOrigin(origins = "*")
    public String getAll() {
        String retorno = "";
        for (Livro livro : acervo.getAll()) {
            retorno += livro.toString() + "";
        }
        return retorno;
    }

    @GetMapping("/titulos")
    @CrossOrigin(origins = "*")
    public String getTitulos() {
        String retorno = "";
        for (Livro livro : acervo.getAll()) {
            retorno += " " + livro.getTitulo();
        }
        return retorno;
    }

    @GetMapping("/autores")
    @CrossOrigin(origins = "*")
    public String getAutores() {
        Set<String> set = new HashSet<>();
        for (Livro livro : acervo.getAll()) {
            set.add(livro.getAutor().getNome());
        }
        return set.toString();
    }

    @GetMapping("/livrosporano")
    @CrossOrigin(origins = "*")
    public String getLivrosPorAno(@RequestParam(value = "ano") int ano) {
        String retorno = "";
        for (Livro livro : acervo.getAll()) {
            if (livro.getAno() == ano) {
                retorno += "" + livro.toString();
            }
        }
        return retorno;
    }
    ///biblioteca/livrosporano?ano=2026

    @GetMapping("/desatualizados/{ano}")
    @CrossOrigin(origins = "*")
    public String getDesatualizadosAno(@PathVariable(value = "ano") int ano) {
        String retorno = "";
        for (Livro livro : acervo.getAll()) {
            if (livro.getAno() < ano) {
                retorno += "" + livro.toString();
            }
        }
        return retorno;
    }

    @GetMapping("/livrosautor/{autor}/ano/{ano}")
    @CrossOrigin(origins = "*")
    public String getLivrosDoAutorNoAno(@PathVariable(value = "autor") String autor, @PathVariable(value = "ano") int ano) {
        String retorno = "";
        Autor autorEndereco = null;
        for (Livro livro : acervo.getAll()) {
            if (livro.getAno() == ano && livro.getAutor().getNome().equals(autor)) {
                retorno += "" + livro.toString();
                if (autorEndereco == null)
                    autorEndereco = livro.getAutor();
            }
        }

        if (autorEndereco != null) {
            estAutor.incrementaNConsulta(autorEndereco);
        }

        return retorno;
    }



    @PostMapping("/livros")
    public ResponseEntity<Livro> cadastraLivroNovo(@RequestBody Livro livro) {
        acervo.cadastraLivroNovo(livro);
        return ResponseEntity
            .created(URI.create("/livros/" + livro.getId()))
            .body(livro);
    }
    //*
    // ta funcionando, se nao estiver ve se o enviroment no postman esta como
    // o Codespaces e se o baseUrl esta certo e se termina com '/' ou nao, pois
    // se tiver {{baseUrl}}/biblioteca/livros e o baseUrl terminar com / vai dar
    // not found 404 */




    @PutMapping("/livros/{id}")
    public ResponseEntity putMethodName(@PathVariable int id, @RequestBody Livro novoLivro) {


        for (Livro livroExistente : acervo.getAll()) {

        if (livroExistente.getId() == id) {

            livroExistente.setTitulo(novoLivro.getTitulo());
            livroExistente.setAutor(novoLivro.getAutor());

            acervo.cadastraLivroNovo(livroExistente);


            return ResponseEntity.ok(livroExistente);
            }
        }


        return ResponseEntity.notFound().build();
        }


    @DeleteMapping("/livros/{codigo}")
    public ResponseEntity<Void> removeLivro(@PathVariable long codigo) {
        return acervo.removeLivro(codigo)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }



}
