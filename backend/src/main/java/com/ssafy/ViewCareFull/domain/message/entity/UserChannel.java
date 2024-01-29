package com.ssafy.ViewCareFull.domain.message.entity;

import com.ssafy.ViewCareFull.domain.message.dto.SSEMessageDto;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Getter
public class UserChannel {

  private final Many<SSEMessageDto> sink;

  public UserChannel() {
    this.sink = Sinks.many().multicast().directAllOrNothing();
  }

  public Flux<SSEMessageDto> asFlux() {
    return this.sink.asFlux();
  }

  public void send(SSEMessageDto message) {
    this.sink.tryEmitNext(message);
  }

  public boolean isEmpty() {
    return this.sink.currentSubscriberCount() == 0;
  }

}
