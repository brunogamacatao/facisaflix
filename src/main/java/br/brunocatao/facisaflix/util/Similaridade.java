package br.brunocatao.facisaflix.util;

import br.brunocatao.facisaflix.entities.Usuario;

public interface Similaridade {
  double calcula(Usuario u1, Usuario u2);
}
