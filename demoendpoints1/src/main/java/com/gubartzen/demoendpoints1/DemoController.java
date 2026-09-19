package com.gubartzen.demoendpoints1;

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
    public String getLivros() {
        String retorno = "";
        for (Livro livro : acervo.getLivros()) {
            retorno += livro.toString() + "";
        }
        return retorno;
    }

    @GetMapping("/titulos")
    @CrossOrigin(origins = "*")
    public String getTitulos() {
        String retorno = "";
        for (Livro livro : acervo.getLivros()) {
            retorno += " " + livro.getTitulo();
        }
        return retorno;
    }

    @GetMapping("/autores")
    @CrossOrigin(origins = "*")
    public String getAutores() {
        Set<String> set = new HashSet<>();
        for (Livro livro : acervo.getLivros()) {
            set.add(livro.getAutor());
        }
        return set.toString();
    }

    @GetMapping("/livrosporano")
    @CrossOrigin(origins = "*")
    public String getLivrosPorAno(@RequestParam(value = "ano") int ano) {
        String retorno = "";
        for (Livro livro : acervo.getLivros()) {
            if (livro.getAno() == ano) {
                retorno += "" + livro.toString();
            }
        }
        return retorno;
    }

    @GetMapping("/desatualizados/{ano}")
    @CrossOrigin(origins = "*")
    public String getDesatualizadosAno(@PathVariable(value = "ano") int ano) {
        String retorno = "";
        for (Livro livro : acervo.getLivros()) {
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
        for (Livro livro : acervo.getLivros()) {
            if (livro.getAno() == ano && livro.getAutor().equals(autor)) {
                retorno += "" + livro.toString();
            }
        }

        estAutor.incrementaNConsulta(autor);

        return retorno;
    }



    @PostMapping("/livros")
    public ResponseEntity<Livro> cadastraLivroNovo(@RequestBody Livro livro) {
        boolean ok = acervo.cadastraLivro(livro);

        return ResponseEntity
            .created(URI.create("/livros/" + livro.getId()))
            .body(livro);
    }




    @PutMapping("livros/{id}")
    public boolean putMethodName(@PathVariable int id, @RequestBody Livro novoLivro) {


        boolean aux = false;
        if (acervo.getLivros().size() > 0) {
            Iterator it = acervo.getLivros().iterator();


            while (it.hasNext()) {
                Livro livro = (Livro) it.next();
                if (livro.getId() == id) {
                    it.remove();
                    acervo.getLivros().add(novoLivro);
                    aux = true;
                    break;
                }

            }

        }
        return aux;
    }

    @DeleteMapping("livros/{id}")
    @CrossOrigin(origins = "*")
    public boolean removeLivro(@PathVariable int id) {
        boolean aux = false;
        if (acervo.getLivros().size() > 0) {
            Iterator it = acervo.getLivros().iterator();


            while (it.hasNext()) {
                Livro livro = (Livro) it.next();
                if (livro.getId() == id) {
                    it.remove();
                    aux = true;
                    break;
                }

            }

        }
        return aux;
    }


}
