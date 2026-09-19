package com.gubartzen.demoendpoints1jpa;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/est")
public class Controller {
    private IAcervo acervo;
    private EstatisticaAutor estAutor;

    @Autowired
    public Controller(IAcervo acervo, EstatisticaAutor estAutor) {
        this.acervo = acervo;
        this.estAutor = estAutor;
    }

    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public String getSaudacao() {
        return "Bem vindo as estatísticas!";
    }

    @GetMapping("listaAutores")
    @CrossOrigin(origins = "*")
    public String getListaAutores() {

        return acervo.listaAutores().toString();
    }

    @GetMapping("autoresMaisConsultados")
    @CrossOrigin(origins = "*")
    public String getMaisConsultados() {

        HashMap<Long, Integer> autorNConsultas = this.estAutor.getAutorNConsultas();

        if (autorNConsultas.isEmpty())
            return "Não há autores";

        Set<Autor> autores = new HashSet<>();

        for (Long id: autorNConsultas.keySet()) {
            autores.add(estAutor.findById(id));
        }

        Set<String> autoresMaisConsultados = new HashSet<>();

        int maiorNConsultas = 0;

        for (Autor autor: autores) {
            int aux = autor.getNConsultas();

            if (aux > maiorNConsultas) {
                autoresMaisConsultados.clear();
                autoresMaisConsultados.add(autor.getNome());
                maiorNConsultas = aux;
            } else if (aux == maiorNConsultas) {
                autoresMaisConsultados.add(autor.getNome());
            }

        }

        if (maiorNConsultas == 0) {
            return "Nenhum autor foi consultado";
        }

        if (autoresMaisConsultados.size() > 1)
            return "Autores mais consultados: "+autoresMaisConsultados.toString()+", "+maiorNConsultas+" consultas";


        return "Autor mais consultado: "+autoresMaisConsultados.toString()+", "+maiorNConsultas+" consultas";


    }


    @GetMapping("autoresMenosConsultados")
    @CrossOrigin(origins = "*")
    public String getMenosConsultados() {

        HashMap<Long, Integer> autorNConsultas = this.estAutor.getAutorNConsultas();

        if (autorNConsultas.isEmpty())
            return "Não há autores";

        Set<Autor> autores = new HashSet<>();

        for (Long id: autorNConsultas.keySet()) {
            autores.add(estAutor.findById(id));
        }

        Set<String> autoresMenosConsultados = new HashSet<>();

        int menorNConsultas = Integer.MAX_VALUE;

        for (Autor autor: autores) {
            int aux = autor.getNConsultas();

            if (aux < menorNConsultas) {
                autoresMenosConsultados.clear();
                autoresMenosConsultados.add(autor.getNome());
                menorNConsultas = aux;
            } else if (aux == menorNConsultas) {
                autoresMenosConsultados.add(autor.getNome());
            }

        }

        if (menorNConsultas == 0) {
            return "Nenhum autor foi consultado";
        }

        if (autoresMenosConsultados.size() > 1)
            return "Autores menos consultados: "+autoresMenosConsultados.toString()+", "+menorNConsultas+" consultas";


        return "Autor menos consultado: "+autoresMenosConsultados.toString()+", "+menorNConsultas+" consultas";


    }




}
