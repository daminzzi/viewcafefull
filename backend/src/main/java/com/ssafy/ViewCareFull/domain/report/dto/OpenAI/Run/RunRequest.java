package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Run;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RunRequest {

  @JsonProperty("assistant_id")
  private final String assistantId;
}
