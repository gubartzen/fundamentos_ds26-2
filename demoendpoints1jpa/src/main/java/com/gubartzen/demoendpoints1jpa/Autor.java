package com.gubartzen.demoendpoints1jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String nome;

    private int nConsultas;


    public Autor() {}

    public Autor(String nome) {
        this.nome = nome;
        this.nConsultas = 0;
    }

    public Long getId() { return id;}


    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
    public int getNConsultas() { return nConsultas; }

    public void setNConsultas(int nConsultas) { this.nConsultas = nConsultas; }
}
