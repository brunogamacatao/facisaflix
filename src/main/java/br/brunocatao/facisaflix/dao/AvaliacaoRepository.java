package br.brunocatao.facisaflix.dao;

import br.brunocatao.facisaflix.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository <Avaliacao, Long> {
}
