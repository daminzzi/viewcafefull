package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIMessageResponse {

  String id;
  String object;
  @JsonProperty("created_at")
  Long createdAt;
  @JsonProperty("thread_id")
  String threadId;
  String role;
  ContentDto[] contentDto;
  @JsonProperty("file_ids")
  String[] fileIds;
  @JsonProperty("assistant_id")
  String assistantId;
  @JsonProperty("run_id")
  String runId;
  Map<String, Object> metadata;

}
