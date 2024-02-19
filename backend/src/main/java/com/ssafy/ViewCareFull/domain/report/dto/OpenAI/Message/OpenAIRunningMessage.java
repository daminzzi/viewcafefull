package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRunningMessage {

  private String id;
  private String object;
  @JsonProperty("created_at")
  private long createdAt;
  @JsonProperty("run_id")
  private String runId;
  @JsonProperty("assistant_id")
  private String assistantId;
  @JsonProperty("thread_id")
  private String threadId;
  private String type;
  private String status;
  @JsonProperty("cancelled_at")
  private long cancelledAt;
  @JsonProperty("completed_at")
  private Long completedAt;
  @JsonProperty("expires_at")
  private Long expiresAt;
  @JsonProperty("failed_at")
  private Long failedAt;
  @JsonProperty("last_error")
  private String lastError;
  @JsonProperty("step_details")
  private StepDetails stepDetails;
  private Usage usage;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class StepDetails {

    private String type;
    @JsonProperty("message_creation")
    private MessageCreation messageCreation;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MessageCreation {

    @JsonProperty("message_id")
    private String messageId;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Usage {

    @JsonProperty("prompt_tokens")
    private int promptTokens;
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @JsonProperty("total_tokens")
    private int totalTokens;

  }
}
