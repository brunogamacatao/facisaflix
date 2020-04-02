package br.brunocatao.facisaflix.dao;

import br.brunocatao.facisaflix.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  List<Usuario> findAllByNome(String nome);
}
