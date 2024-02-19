package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAICompletedMessage {

  private String id;
  private String object;
  @JsonProperty("created_at")
  private long createdAt;
  @JsonProperty("thread_id")
  private String threadId;
  private String role;
  private ContentDto[] content;
  @JsonProperty("file_ids")
  private String[] fileIds;
  @JsonProperty("assistant_id")
  private String assistantId;
  @JsonProperty("run_id")
  private String runId;
  private Map<String, Object> metadata;

}
