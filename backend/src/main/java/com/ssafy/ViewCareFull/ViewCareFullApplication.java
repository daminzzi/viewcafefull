package com.ssafy.ViewCareFull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ViewCareFullApplication {

  public static void main(String[] args) {
    SpringApplication.run(ViewCareFullApplication.class, args);
  }

}
