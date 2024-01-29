package com.ssafy.ViewCareFull.domain.message.service;

import com.ssafy.ViewCareFull.domain.message.dto.SSEMessageDto;
import com.ssafy.ViewCareFull.domain.message.entity.UserChannel;
import com.ssafy.ViewCareFull.domain.message.entity.UserChannels;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final UserChannels userChannels;


  public Flux<SSEMessageDto> subscribe(Long id) {
    return userChannels.getFlux(id);
  }

  public void send(SSEMessageDto message) {
    UserChannel channel = userChannels.getChannel(message.getReceiver()).orElse(null);
    if (channel == null) {
      return;
    }
    if (channel.isEmpty()) {
      removeEmptyChannel(message.getReceiver());
      return;
    }
    channel.send(message);
  }

  private void removeEmptyChannel(Long id) {
    userChannels.removeChannel(id);
  }

}
