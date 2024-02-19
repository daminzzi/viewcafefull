package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Assistant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistantRequest {

  private String model;
  private String instructions;

  public AssistantRequest(String model) {
    this.model = model;
  }
}
