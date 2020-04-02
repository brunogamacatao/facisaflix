package br.brunocatao.facisaflix.controllers;

import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.services.AvaliacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("filmes")
public class FilmesController {
  private AvaliacaoService avaliacaoService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Filme> getFilmes() {
    return avaliacaoService.getFilmes();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Filme getById(@PathVariable Long id) {
    return avaliacaoService.getFilme(id).get();
  }
}
