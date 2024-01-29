package com.ssafy.ViewCareFull;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticRoutingConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/static/", "file:/app/static/");
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/static/swagger-ui/", "file:/app/static/swagger-ui/");
  }

  @Override
  public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("https://i10a601.p.ssafy.io:8030", "https://i10a601.p.ssafy.io:8090",
            "https://i10a601.p.ssafy.io:8091", "http://localhost:3000", "http://localhost:5173")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
        .allowCredentials(true)
        .exposedHeaders("Authorization");
  }
}
