package com.gubartzen;

import com.google.gson.Gson;



public class App {

    public App() {

    }
    public static void main(String[] args) {
        Pessoa p = new Pessoa("Joao", "03424239");
        Gson gson = new Gson();
        String pessoaJson = gson.toJson(p);
        System.out.println(pessoaJson);
    }
}
