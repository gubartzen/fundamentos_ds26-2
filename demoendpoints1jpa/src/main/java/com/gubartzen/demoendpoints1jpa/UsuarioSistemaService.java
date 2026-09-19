package com.gubartzen.demoendpoints1jpa;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioSistemaService(UsuarioSistemaRepository usuarioSistemaRepository,
                                 PasswordEncoder passwordEncoder) {
        this.usuarioSistemaRepository = usuarioSistemaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean cadastraUsuarioSistema(NovoUsuarioSistemaRequest novoUsuario) {
        if (novoUsuario == null ||
            novoUsuario.username() == null ||
            novoUsuario.senha() == null) {
            return false;
        }

        String username = novoUsuario.username().trim();
        String senha = novoUsuario.senha().trim();

        if (username.isEmpty() ||
            senha.isEmpty() ||
            usuarioSistemaRepository.existsByUsername(username)) {
            return false;
        }

        var novoUsuarioSistema = new UsuarioSistema(
            username,
            passwordEncoder.encode(senha),
            novoUsuario.perfilNormalizado()
        );

        usuarioSistemaRepository.save(novoUsuarioSistema);
        return true;
    }
}
