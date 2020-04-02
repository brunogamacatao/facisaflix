package br.brunocatao.facisaflix.services;

import br.brunocatao.facisaflix.dto.ScoreFilme;
import br.brunocatao.facisaflix.dto.ScoreUsuario;
import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.entities.Usuario;
import br.brunocatao.facisaflix.util.Similaridade;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoService {
  List<Filme> getFilmes();
  List<Usuario> getUsuarios();
  Optional<Usuario> getUsuario(Long id);
  List<Usuario> getUsuariosPorNome(String nome);
  Optional<Filme> getFilme(Long id);
  List<Filme> getFilmesPorNome(String nome);
  List<ScoreUsuario> getUsuariosSimilares(Usuario usuario, Similaridade calculador);
  List<ScoreFilme> getFilmesRecomendados(Usuario usuario, Similaridade calculador);
}
