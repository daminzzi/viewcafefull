package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Run;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RunResponse {

  String id;
  String object;
  @JsonProperty("created_at")
  Long createdAt;
  @JsonProperty("assistant_id")
  String assistantId;
  @JsonProperty("thread_id")
  String threadId;
  String status;
  @JsonProperty("started_at")
  Long startedAt;
  @JsonProperty("expires_at")
  Long expiresAt;
  @JsonProperty("cancelled_at")
  Long cancelledAt;
  @JsonProperty("failed_at")
  Long failedAt;
  @JsonProperty("completed_at")
  Long completedAt;
  @JsonProperty("last_error")
  String lastError;
  String model;
  String instructions;
  List<Type> tools;
  @JsonProperty("file_ids")
  List<String> fileIds;
  Map<String, Object> metadata;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Type {

    String type;
  }
}
