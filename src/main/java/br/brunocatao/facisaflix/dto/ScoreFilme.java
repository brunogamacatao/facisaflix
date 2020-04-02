package br.brunocatao.facisaflix.dto;

import br.brunocatao.facisaflix.entities.Filme;
import lombok.Data;

import java.io.Serializable;

@Data
public class ScoreFilme implements Serializable {
  private static final long serialVersionUID = -5775291088263097015L;

  private Filme filme;
  private double score;

  public ScoreFilme() {
  }

  public ScoreFilme(Filme filme, double score) {
    this.filme = filme;
    this.score = score;
  }
}
