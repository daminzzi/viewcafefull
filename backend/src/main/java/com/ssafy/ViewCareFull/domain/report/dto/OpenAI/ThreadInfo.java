package com.ssafy.ViewCareFull.domain.report.dto.OpenAI;

import com.ssafy.ViewCareFull.domain.report.util.RestApiUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ThreadInfo {
  private final String id;
  private final String apiKey;
  public void deleteThread() {
    RestApiUtil.delete(getApiKey(), "/threads/" + getId(), String.class);
  }
}
