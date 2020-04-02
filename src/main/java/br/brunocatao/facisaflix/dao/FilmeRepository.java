package br.brunocatao.facisaflix.dao;

import br.brunocatao.facisaflix.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
  List<Filme> findAllByNome(String nome);
}
