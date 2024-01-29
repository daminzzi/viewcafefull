package com.ssafy.ViewCareFull.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SSEMessageDto {

  private Long id;
  private Long sender;
  private Long receiver;
  private String message;
  private String time;

}
