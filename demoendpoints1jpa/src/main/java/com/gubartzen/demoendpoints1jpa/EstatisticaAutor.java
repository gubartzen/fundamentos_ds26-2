package com.gubartzen.demoendpoints1jpa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;

@Component
public class EstatisticaAutor {

    @Autowired
    private AutorRepository autorRepository;

    public EstatisticaAutor() {}


    public EstatisticaAutor(AutorRepository autorRepository, Long id) {
        this.autorRepository = autorRepository;
    }

    public Autor findById(Long id) {
        return autorRepository.findById(id).orElse(null);
    }

    protected HashMap<Long, Integer> getAutorNConsultas() {
        List<Autor> lista = autorRepository.findAll();

        HashMap<Long, Integer> autorNConsultas = new HashMap<>();
        for (Autor autor: lista) {
            autorNConsultas.put(autor.getId(), autor.getNConsultas());
        }
        return autorNConsultas;
    }


    protected void adicionaAutor(String autor) {
        Autor autorAutor = new Autor(autor);

        autorRepository.save(autorAutor);

    }
    protected boolean incrementaNConsulta(Autor autor) {
        autor.setNConsultas((autor.getNConsultas()+1));
        autorRepository.save(autor); 
        return true;
    }

}
