package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssistantResponse {

  private String id;
  private String object;
  @JsonProperty("created_at")
  private long createdAt;
  private String name;
  private String description;
  private String model;
  private String instructions;
  private List<String> tools;
  @JsonProperty("file_ids")
  private List<String> fileIds;
  private Map<String, Object> metadata;
}
