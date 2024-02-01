package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIMessageRequest {

  private String role;
  private String content;
}
