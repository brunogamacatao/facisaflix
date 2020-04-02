package br.brunocatao.facisaflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FacisaflixApplication {

  public static void main(String[] args) {
    SpringApplication.run(FacisaflixApplication.class, args);
  }

}
