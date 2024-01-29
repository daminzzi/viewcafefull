package com.ssafy.ViewCareFull.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageRequestDto {

  private String to;
  private String title;
  private String content;

}
