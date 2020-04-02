package br.brunocatao.facisaflix.controllers;

import br.brunocatao.facisaflix.dto.ScoreFilme;
import br.brunocatao.facisaflix.dto.ScoreUsuario;
import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.entities.Usuario;
import br.brunocatao.facisaflix.services.AvaliacaoService;
import br.brunocatao.facisaflix.util.CalculadorDeSimilaridade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("recomendacoes")
public class RecomendacoesController {
  private AvaliacaoService avaliacaoService;

  /**
   * Recomenda usuários similares
   * @param id Id do usuário para o qual se quer recomendar similares
   * @return Lista ordenada de scores de usuários similares
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/usuarios/{id}", method = RequestMethod.GET)
  public List<ScoreUsuario> getUsuariosSimilares(@PathVariable Long id) {
    Usuario usuario = avaliacaoService.getUsuario(id).get();
    return avaliacaoService.getUsuariosSimilares(usuario, CalculadorDeSimilaridade::getSimilaridadePearson);
  }

  /**
   * Recomenda filmes para um usuário
   * @param id Id do usuário para o qual se quer recomendar um filme
   * @return Lista ordenada de scores de filmes recomendados
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/filmes/{id}", method = RequestMethod.GET)
  public List<ScoreFilme> getFilmesRecomendados(@PathVariable Long id) {
    Usuario usuario = avaliacaoService.getUsuario(id).get();
    return avaliacaoService.getFilmesRecomendados(usuario, CalculadorDeSimilaridade::getSimilaridadePearson);
  }
}
