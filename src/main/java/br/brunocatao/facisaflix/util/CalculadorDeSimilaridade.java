package br.brunocatao.facisaflix.util;

import br.brunocatao.facisaflix.entities.Avaliacao;
import br.brunocatao.facisaflix.entities.Filme;
import br.brunocatao.facisaflix.entities.Usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculadorDeSimilaridade {
  /**
   * Cálculo da similaridade baseado na fórmula da distância euclidiana entre
   * dois pontos. A distância entre dois pontos P1(x1, y1) e P2(x2, y2) é:
   * RAIZ_QUADRADA((x1-x2)^2 + (y1-y2)^2))
   * Porém, como aqui queremos a similaridade, então invertemos a fórmula:
   * SIMILARIDADE = 1 / (1 + DISTÂNCIA)
   *
   * @param u1 Usuário 1
   * @param u2 Usuário 2
   * @return A similaridade (inverso da distância euclidiana) entre os usuários
   */
  public static double getSimilaridadeEuclidiana(Usuario u1, Usuario u2) {
    Set<Filme> filmesQueOsDoisViram = getFilmesQueAmbosViram(u1, u2);

    // Se os dois não viram nenhum filme em comum
    if (filmesQueOsDoisViram.isEmpty()) {
      return 0.0; // não há similaridade entre os dois
    }

    // Vamos calcular a similaridade pela Distância Euclidiana
    // 1. Calculamos a distância pela a soma dos quadrados das diferenças
    double somaDosQuadrados = filmesQueOsDoisViram.stream() // para cada filme que os dois viram
        .map(f -> getNota(f, u1) - getNota(f, u2)) // calcula as diferenças
        .map(diferenca -> diferenca * diferenca) // eleva ao quadrado
        .reduce(0.0, Double::sum); // calcula a soma

    // 2. Inverte a distância para calcular a similaridade
    return 1.0 / (1.0 + somaDosQuadrados);
  }

  public static double getSimilaridadePearson(Usuario u1, Usuario u2) {
    Set<Filme> filmesQueOsDoisViram = getFilmesQueAmbosViram(u1, u2);

    // Se os dois não viram nenhum filme em comum
    if (filmesQueOsDoisViram.isEmpty()) {
      return 0.0; // não há similaridade entre os dois
    }

    // Soma todas as preferências
    double soma1 = filmesQueOsDoisViram.stream().map(f -> getNota(f, u1)).reduce(0.0, Double::sum);
    double soma2 = filmesQueOsDoisViram.stream().map(f -> getNota(f, u2)).reduce(0.0, Double::sum);

    // Soma dos quadrados
    double somaQ1 = filmesQueOsDoisViram.stream().map(f -> getNota(f, u1)).map(n -> n * n).reduce(0.0, Double::sum);
    double somaQ2 = filmesQueOsDoisViram.stream().map(f -> getNota(f, u2)).map(n -> n * n).reduce(0.0, Double::sum);

    // Soma dos produtos
    double somaP = filmesQueOsDoisViram.stream().map(f -> getNota(f, u1) * getNota(f, u2)).reduce(0.0, Double::sum);

    // Calcula o Score de Pearson
    int n = filmesQueOsDoisViram.size();
    double numerador = somaP - (soma1 * soma2 / n);
    double denominador = Math.sqrt((somaQ1-Math.pow(soma1,2)/n)*(somaQ2-Math.pow(soma2,2)/n));

    if (denominador == 0.0) {
      return 0.0;
    }

    return numerador / denominador;
  }

  private static Set<Filme> getFilmesQueAmbosViram(Usuario u1, Usuario u2) {
    Set<Filme> filmesAvaliados1 = u1.getAvaliacoes().stream().map(a -> a.getFilme()).collect(Collectors.toSet());
    Set<Filme> filmesAvaliados2 = u2.getAvaliacoes().stream().map(a -> a.getFilme()).collect(Collectors.toSet());

    // Fazendo a intersecção dos dois conjuntos
    Set<Filme> filmesQueOsDoisViram = new HashSet<>(filmesAvaliados1);
    filmesQueOsDoisViram.retainAll(filmesAvaliados2);

    return filmesQueOsDoisViram;
  }

  public static double getNota(Filme filme, Usuario usuario) {
    for (Avaliacao a : filme.getAvaliacoes()) {
      if (a.getUsuario().equals(usuario)) {
        return a.getNota();
      }
    }

    return 0.0;
  }
}
