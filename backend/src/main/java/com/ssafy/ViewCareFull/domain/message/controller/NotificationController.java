package com.ssafy.ViewCareFull.domain.message.controller;

import com.ssafy.ViewCareFull.domain.message.dto.SSEMessageDto;
import com.ssafy.ViewCareFull.domain.message.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notification")
@Slf4j
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping(value = "/test")
  public Flux<ServerSentEvent<String>> test() {
    Flux<String> map = Flux.just("1", "2", "3");
    return map.map(s -> ServerSentEvent.builder(s).build());
  }

  @GetMapping(value = "/subscribe/{id}")
  public Flux<ServerSentEvent<SSEMessageDto>> subscribe(@PathVariable Long id) {
    Flux<SSEMessageDto> subscribe = notificationService.subscribe(id);
    return subscribe.map(s -> ServerSentEvent.builder(s).build());
  }

  @GetMapping(value = "/send/{id}")
  public void send(@PathVariable Long id) {
    notificationService.send(SSEMessageDto.builder()
        .sender(1L)
        .receiver(id)
        .message("안녕하세요")
        .build());
  }

}
