package com.ssafy.ViewCareFull.domain.message.entity;

import com.ssafy.ViewCareFull.domain.message.dto.SSEMessageDto;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class UserChannels {

  private final ConcurrentHashMap<Long, UserChannel> userChannels = new ConcurrentHashMap<>();

  public Optional<UserChannel> getChannel(Long id) {
    return Optional.ofNullable(userChannels.getOrDefault(id, null));
  }

  public void addChannel(Long id, UserChannel userChannel) {
    userChannels.put(id, userChannel);
  }

  public void removeChannel(Long id) {
    userChannels.remove(id);
  }

  public Flux<SSEMessageDto> getFlux(Long id) {
    userChannels.computeIfAbsent(id, k -> new UserChannel());
    return userChannels.get(id).asFlux();
  }

}
