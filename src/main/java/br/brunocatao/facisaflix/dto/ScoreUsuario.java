package br.brunocatao.facisaflix.dto;

import br.brunocatao.facisaflix.entities.Usuario;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class ScoreUsuario implements Serializable {
  private static final long serialVersionUID = -4452632495160545683L;

  private Usuario usuario;
  private double score;

  public ScoreUsuario() {
  }

  public ScoreUsuario(Usuario usuario, double score) {
    this.usuario = usuario;
    this.score = score;
  }
}
