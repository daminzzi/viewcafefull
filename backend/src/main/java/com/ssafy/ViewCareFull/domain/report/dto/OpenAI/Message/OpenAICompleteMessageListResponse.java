package com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.ViewCareFull.domain.report.util.OpenAIChkUtil;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAICompleteMessageListResponse {

  private String object;
  private OpenAICompletedMessage[] data;
  @JsonProperty("first_id")
  private String firstId;
  @JsonProperty("last_id")
  private String lastId;
  @JsonProperty("has_more")
  private boolean hasMore;

  public Optional<OpenAICompletedMessage> getReportHealthInfo() {
    for (OpenAICompletedMessage result : data) {
      if (result.getRole().equals("assistant")&& OpenAIChkUtil.contentChk(result)) {
        return Optional.of(result);
      }
    }
    return Optional.empty();
  }
}
