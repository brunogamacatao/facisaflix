package br.brunocatao.facisaflix.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Usuario implements Serializable {
  private static final long serialVersionUID = -4352561541531968720L;

  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
  private List<Avaliacao> avaliacoes = new ArrayList<>();
}
