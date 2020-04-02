package br.brunocatao.facisaflix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Avaliacao implements Serializable {
  private static final long serialVersionUID = -3560633745770294300L;

  @Id
  @GeneratedValue
  private Long id;
  @JsonIgnore
  @ManyToOne
  private Usuario usuario;
  @ManyToOne(fetch = FetchType.EAGER)
  private Filme filme;
  private double nota;
}
