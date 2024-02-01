package com.ssafy.ViewCareFull.domain.report.util;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class RestApiUtil {

  public static <T, U> U post(String apiKey, String uri, T body, Class<U> returnType) {
    return WebClient.create("https://api.openai.com/v1").post().uri(uri).headers(header -> {
      header.setContentType(MediaType.APPLICATION_JSON);
      header.setBearerAuth(apiKey);
      header.add("OpenAI-Beta", "assistants=v1");
    }).bodyValue(body).retrieve().bodyToMono(returnType).block();
  }

  public static <T> T get(String apiKey, String uri, Class<T> returnType) {
    return WebClient.create("https://api.openai.com/v1").get().uri(uri).headers(header -> {
      header.setContentType(MediaType.APPLICATION_JSON);
      header.setBearerAuth(apiKey);
      header.add("OpenAI-Beta", "assistants=v1");
    }).retrieve().bodyToMono(returnType).block();
  }

  public static <T> void delete(String apiKey, String uri, Class<T> returnType) {
    WebClient.create("https://api.openai.com/v1").delete().uri(uri).headers(header -> {
      header.setContentType(MediaType.APPLICATION_JSON);
      header.setBearerAuth(apiKey);
      header.add("OpenAI-Beta", "assistants=v1");
    }).retrieve().bodyToMono(returnType).block();
  }
}
