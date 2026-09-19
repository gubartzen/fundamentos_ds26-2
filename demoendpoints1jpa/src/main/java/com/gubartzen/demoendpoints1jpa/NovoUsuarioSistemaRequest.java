package com.gubartzen.demoendpoints1jpa;

public record NovoUsuarioSistemaRequest(
    String username,
    String senha,
    String perfil
) {
    public String perfilNormalizado() {
        if (perfil == null || perfil.isBlank()) {
            return "CONSULTA";
        }

        var perfilEmMaiusculas = perfil.trim().toUpperCase();
        if ("ADMIN".equals(perfilEmMaiusculas)) {
            return "ADMIN";
        }

        return "CONSULTA";
    }
}


