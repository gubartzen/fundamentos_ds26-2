package com.gubartzen.demoendpoints1jpa;


import java.util.List;


import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByAutor(String autor);
    Livro findByTitulo(String titulo);
}
