package com.gubartzen.demoendpoints1;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class LivrosRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Livro> livroRowMapper =
            new RowMapper<Livro>() {
        @Override
        public Livro mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Livro(rs.getLong("id"), rs.getString("titulo"),
                             rs.getString("autor"), rs.getInt("ano"));
        }
    };

    @Autowired
    public LivrosRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        criarTabelaSeNecessario();
    }

    private void criarTabelaSeNecessario() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS livros (
                id BIGINT PRIMARY KEY,
                titulo VARCHAR(255) NOT NULL,
                autor VARCHAR(255) NOT NULL,
                ano INT NOT NULL)
        """);
    }

    public void save(Livro livro) {
    int atualizados = jdbcTemplate.update(
        "UPDATE livros SET titulo = ?, autor = ?, ano = ? WHERE id = ?",
        livro.getTitulo(), livro.getAutor(), livro.getAno(), livro.getId());

    if (atualizados == 0) {
        jdbcTemplate.update(
            "INSERT INTO livros (id, titulo, autor, ano) VALUES (?, ?, ?, ?)",
            livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAno());
    }
    }

    public List<Livro> findAll() {
        return jdbcTemplate.query(
            "SELECT id, titulo, autor, ano FROM livros",
            livroRowMapper);
    }

    public List<Livro> findByAutor(String autor) {
        return jdbcTemplate.query(
            "SELECT id, titulo, autor, ano FROM livros WHERE autor = ?",
            livroRowMapper, autor);
    }

    public int count() {

        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM livros", Integer.class);
    }


}
