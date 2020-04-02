package br.brunocatao.facisaflix.services;

import br.brunocatao.facisaflix.dao.AvaliacaoRepository;
import br.brunocatao.facisaflix.dao.FilmeRepository;
import br.brunocatao.facisaflix.dao.UsuarioRepository;
import br.brunocatao.facisaflix.dto.ScoreFilme;
import br.brunocatao.facisaflix.dto.ScoreUsuario;
import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.entities.Usuario;
import br.brunocatao.facisaflix.util.Similaridade;
import static br.brunocatao.facisaflix.util.CalculadorDeSimilaridade.getNota;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService {
  private AvaliacaoRepository avaliacaoRepository;
  private FilmeRepository filmeRepository;
  private UsuarioRepository usuarioRepository;

  @Override
  public List<Filme> getFilmes() {
    return filmeRepository.findAll();
  }

  @Override
  public List<Usuario> getUsuarios() {
    return usuarioRepository.findAll();
  }

  @Override
  public Optional<Usuario> getUsuario(Long id) {
    return usuarioRepository.findById(id);
  }

  @Override
  public List<Usuario> getUsuariosPorNome(String nome) {
    return usuarioRepository.findAllByNome(nome);
  }

  @Override
  public Optional<Filme> getFilme(Long id) {
    return filmeRepository.findById(id);
  }

  @Override
  public List<Filme> getFilmesPorNome(String nome) {
    return filmeRepository.findAllByNome(nome);
  }

  @Override
  public List<ScoreUsuario> getUsuariosSimilares(Usuario usuario, Similaridade calculador) {
    return usuarioRepository.findAll().stream() // para todos os usuários u
        .filter(u -> u != usuario) // diferentes do usuário passado como argumento
        .map(u -> new ScoreUsuario(u, calculador.calcula(usuario, u))) // calcula a similaridade
        .sorted((s1, s2) -> Double.compare(s2.getScore(), s1.getScore())) // ordena da maior para menor similaridade
        .collect(Collectors.toList());
  }

  @Override
  public List<ScoreFilme> getFilmesRecomendados(Usuario usuario, Similaridade calculador) {
    Map<Filme, Double> totalPorFilme = new HashMap<>();
    Map<Filme, Double> similaridadePorFilme = new HashMap<>();

    usuarioRepository.findAll().stream() // para todos os usuários u
        .filter(u -> !u.equals(usuario)) // diferentes do usuário passado como argumento
        .map(u -> new ScoreUsuario(u, calculador.calcula(usuario, u))) // calcula a similaridade
        .filter(s -> s.getScore() > 0) // remove os usuários que não tem similaridade  entre si
        .forEach(s -> {
          Set<Filme> filmesQueEuNaoVi = s.getUsuario().getAvaliacoes().stream().map(a -> a.getFilme()).collect(Collectors.toSet());
          filmesQueEuNaoVi.removeAll(usuario.getAvaliacoes().stream().map(a -> a.getFilme()).collect(Collectors.toSet()));

          filmesQueEuNaoVi.forEach(filme -> {
            double tpf = totalPorFilme.containsKey(filme) ? totalPorFilme.get(filme) : 0.0;
            double spf = similaridadePorFilme.containsKey(filme) ? similaridadePorFilme.get(filme) : 0.0;

            tpf += getNota(filme, s.getUsuario()) * s.getScore();
            spf += s.getScore();

            totalPorFilme.put(filme, tpf);
            similaridadePorFilme.put(filme, spf);
          });
        });

    return totalPorFilme.keySet().stream()
        .map(filme -> new ScoreFilme(filme, totalPorFilme.get(filme) / similaridadePorFilme.get(filme)))
        .sorted((s1, s2) -> Double.compare(s2.getScore(), s1.getScore()))
        .collect(Collectors.toList());
  }
}
