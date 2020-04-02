package br.brunocatao.facisaflix.init;

import br.brunocatao.facisaflix.dao.AvaliacaoRepository;
import br.brunocatao.facisaflix.dao.FilmeRepository;
import br.brunocatao.facisaflix.dao.UsuarioRepository;
import br.brunocatao.facisaflix.entities.Avaliacao;
import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.entities.Usuario;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class CarregaDadosIniciais implements InitializingBean {
  private static final Logger log = LoggerFactory.getLogger(CarregaDadosIniciais.class);
  private static Map<String, Filme> filmes = new HashMap<>();

  private AvaliacaoRepository avaliacaoRepository;
  private FilmeRepository filmeRepository;
  private UsuarioRepository usuarioRepository;

  @Override
  public void afterPropertiesSet() throws Exception {
    // se o banco de dados está vazio, povoa
    if (!filmeRepository.findAll().isEmpty()) {
      log.info("O banco de dados já está povoado");
      return;
    }

    log.info("Banco de dados vazio. Povoando ...");
    log.info("Criando os filmes ...");
    criaFilmes();
    log.info("Criando os usuários ...");
    criaUsuarios();
    log.info("Pronto");
  }

  private void criaFilmes() {
    addFilme("Lady in the Water");
    addFilme("Snakes on a Plane");
    addFilme("Just My Luck");
    addFilme("Superman Returns");
    addFilme("You, Me and Dupree");
    addFilme("The Night Listener");
  }

  private void criaUsuarios() {
    addUsuario(AvaliacaoBuilder.create("Lisa Rose")
        .add("Lady in the Water", 2.5)
        .add("Snakes on a Plane", 3.5)
        .add("Just My Luck", 3.0)
        .add("Superman Returns", 3.5)
        .add("You, Me and Dupree", 2.5)
        .add("The Night Listener", 3.0)
        .build());
    addUsuario(AvaliacaoBuilder.create("Gene Seymour")
        .add("Lady in the Water", 3.0)
        .add("Snakes on a Plane", 3.5)
        .add("Just My Luck", 1.5)
        .add("Superman Returns", 5.0)
        .add("You, Me and Dupree", 3.5)
        .add("The Night Listener", 3.0)
        .build());
    addUsuario(AvaliacaoBuilder.create("Michael Phillips")
        .add("Lady in the Water", 2.5)
        .add("Snakes on a Plane", 3.0)
        .add("Superman Returns", 3.5)
        .add("The Night Listener", 4.0)
        .build());
    addUsuario(AvaliacaoBuilder.create("Claudia Puig")
        .add("Snakes on a Plane", 3.5)
        .add("Just My Luck", 3.0)
        .add("Superman Returns", 4.0)
        .add("You, Me and Dupree", 2.5)
        .add("The Night Listener", 4.5)
        .build());
    addUsuario(AvaliacaoBuilder.create("Mick LaSalle")
        .add("Lady in the Water", 3.0)
        .add("Snakes on a Plane", 4.0)
        .add("Just My Luck", 2.0)
        .add("Superman Returns", 3.0)
        .add("You, Me and Dupree", 2.0)
        .add("The Night Listener", 3.0)
        .build());
    addUsuario(AvaliacaoBuilder.create("Jack Matthews")
        .add("Lady in the Water", 3.0)
        .add("Snakes on a Plane", 4.0)
        .add("Superman Returns", 5.0)
        .add("You, Me and Dupree", 3.5)
        .add("The Night Listener", 3.0)
        .build());
    addUsuario(AvaliacaoBuilder.create("Toby")
        .add("Snakes on a Plane", 4.5)
        .add("Superman Returns", 4.0)
        .add("You, Me and Dupree", 1.0)
        .build());
  }

  private void addFilme(String nome) {
    Filme filme = new Filme();
    filme.setNome(nome);
    filmes.put(nome, filmeRepository.save(filme));
  }

  private void addUsuario(Usuario usuario) {
    usuarioRepository.save(usuario);
    avaliacaoRepository.saveAll(usuario.getAvaliacoes());
  }

  // Padrão de projeto Builder
  private static class AvaliacaoBuilder {
    private Usuario usuario;

    private AvaliacaoBuilder(Usuario usuario) {
      this.usuario = usuario;
    }

    public static AvaliacaoBuilder create(String nomeUsuario) {
      Usuario usuario = new Usuario();
      usuario.setNome(nomeUsuario);

      AvaliacaoBuilder builder = new AvaliacaoBuilder(usuario);

      return builder;
    }

    public AvaliacaoBuilder add(String filme, double nota) {
      Avaliacao avaliacao = new Avaliacao();
      avaliacao.setUsuario(this.usuario);
      avaliacao.setFilme(filmes.get(filme));
      avaliacao.setNota(nota);

      this.usuario.getAvaliacoes().add(avaliacao);
      avaliacao.getFilme().getAvaliacoes().add(avaliacao);

      return this;
    }

    public Usuario build() {
      return this.usuario;
    }
  }
}
