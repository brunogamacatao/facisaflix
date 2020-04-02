package br.brunocatao.facisaflix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Filme implements Serializable {
  private static final long serialVersionUID = -5797014118305104250L;

  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  @JsonIgnore
  @OneToMany(mappedBy = "filme")
  private List<Avaliacao> avaliacoes = new ArrayList<>();
}
