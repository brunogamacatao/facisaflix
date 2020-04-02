package br.brunocatao.facisaflix.controllers;

import br.brunocatao.facisaflix.entities.Usuario;
import br.brunocatao.facisaflix.services.AvaliacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("usuarios")
public class UsuariosController {
  private AvaliacaoService avaliacaoService;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/", method = RequestMethod.GET)
  private List<Usuario> getUsuarios() {
    return avaliacaoService.getUsuarios();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Usuario getById(@PathVariable Long id) {
    return avaliacaoService.getUsuario(id).get();
  }
}
