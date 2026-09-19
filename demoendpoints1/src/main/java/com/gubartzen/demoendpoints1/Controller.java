package com.gubartzen.demoendpoints1;

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
    public String getLivros() {

        return acervo.listaAutores().toString();
    }

    @GetMapping("autoresMaisConsultados")
    @CrossOrigin(origins = "*")
    public String getMaisConsultados() {

        HashMap<String, Integer> autorNConsultas = this.estAutor.getAutorNConsultas();

        if (autorNConsultas.isEmpty())
            return "Não há autores";

        Set<String> autores = autorNConsultas.keySet();

        Set<String> autoresMaisConsultados = new HashSet<>();

        int maiorNConsultas = 0;

        for (String autor: autores) {
            int aux = autorNConsultas.get(autor);

            if (aux > maiorNConsultas) {
                autoresMaisConsultados.clear();
                autoresMaisConsultados.add(autor);
                maiorNConsultas = aux;
            } else if (aux == maiorNConsultas) {
                autoresMaisConsultados.add(autor);
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

        HashMap<String, Integer> autorNConsultas = this.estAutor.getAutorNConsultas();

        if (autorNConsultas.isEmpty())
            return "Não há autores";

        Set<String> autores = autorNConsultas.keySet();

        Set<String> autoresMenosConsultados = new HashSet<>();

        int menorNConsultas = Integer.MAX_VALUE;

        for (String autor: autores) {
            int aux = autorNConsultas.get(autor);

            if (aux < menorNConsultas) {
                autoresMenosConsultados.clear();
                autoresMenosConsultados.add(autor);
                menorNConsultas = aux;
            } else if (aux == menorNConsultas) {
                autoresMenosConsultados.add(autor);
            }

        }



        if (autoresMenosConsultados.size() > 1)
            return "Autores menos consultados: "+autoresMenosConsultados.toString()+", "+menorNConsultas+" consultas";


        return "Autor menos consultado: "+autoresMenosConsultados.toString()+", "+menorNConsultas+" consultas";


    }




}
