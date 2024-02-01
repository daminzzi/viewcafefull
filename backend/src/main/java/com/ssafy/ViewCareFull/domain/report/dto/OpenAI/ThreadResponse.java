package com.ssafy.ViewCareFull.domain.report.dto.OpenAI;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThreadResponse {

  private String id;
  private String object;
  @JsonProperty("created_at")
  private long createdAt;
  private Map<String, Object> metadata;
}
