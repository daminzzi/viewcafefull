package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRunningMessageListResponse {

  private String object;
  private OpenAIRunningMessage[] data;
  @JsonProperty("first_id")
  private String firstId;
  @JsonProperty("last_id")
  private String lastId;
  @JsonProperty("has_more")
  private boolean hasMore;

}
