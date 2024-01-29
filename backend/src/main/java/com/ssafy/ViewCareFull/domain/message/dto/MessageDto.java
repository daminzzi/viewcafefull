package com.ssafy.ViewCareFull.domain.message.dto;

import com.ssafy.ViewCareFull.domain.message.entity.Message;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

  private Long id;
  private String from;
  private String title;
  private String content;
  private String time;
  private Boolean isRead;

  public static List<MessageDto> of(List<Message> content) {
    return content.stream().map(MessageDto::of).collect(Collectors.toList());
  }

  public static MessageDto of(Message message) {
    return MessageDto.builder()
        .id(message.getId())
        .from(message.getFromId())
        .title(message.getTitle())
        .content(message.getContent())
        .time(message.getSendDateTime().toString())
        .isRead(message.getIsRead())
        .build();
  }
}
