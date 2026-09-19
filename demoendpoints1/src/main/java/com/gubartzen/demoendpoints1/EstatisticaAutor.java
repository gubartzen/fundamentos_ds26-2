package com.gubartzen.demoendpoints1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EstatisticaAutor {
    HashMap<String, Integer> autorNConsultas = new HashMap<String, Integer>();
    //singleton

    public EstatisticaAutor() {
        this.autorNConsultas = new HashMap<String, Integer>();
    }

    protected HashMap<String, Integer> getAutorNConsultas() {
        return this.autorNConsultas;
    }


    protected void adicionaAutor(String autor) {
        autorNConsultas.put(autor, autorNConsultas.getOrDefault(autor, 0));

    }
    protected boolean incrementaNConsulta(String autor) {

        int nConsultas = autorNConsultas.getOrDefault(autor, autorNConsultas.getOrDefault(autor, -1));

        if (nConsultas == -1) {
            return false;
        }

        autorNConsultas.put(autor, (nConsultas+1));


        return true;
    }

    protected int getNConsultas(String autor) {
        return autorNConsultas.getOrDefault(autor, autorNConsultas.getOrDefault(autor, -1));
    }


}
