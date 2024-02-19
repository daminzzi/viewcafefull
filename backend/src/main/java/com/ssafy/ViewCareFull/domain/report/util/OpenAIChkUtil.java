package com.ssafy.ViewCareFull.domain.report.util;

import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAICompletedMessage;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAIRunningMessage;
import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.Message.OpenAIRunningMessageListResponse;
import java.util.Optional;

public class OpenAIChkUtil {

  public static Optional<String> findRunCompleted(OpenAIRunningMessageListResponse messageListResponse) {
    for (OpenAIRunningMessage rs : messageListResponse.getData()) {
      if (rs.getStatus().equals("completed")) {
        return Optional.of(rs.getRunId());
      }
    }
    return Optional.empty();
  }

  public static boolean contentChk(OpenAICompletedMessage result) {
    if (result.getContent().length == 0) {
      return false;
    }
    String openAIResult = result.getContent()[0].getText().getValue();
    for (String property : MonthlyReport.properties()) {
      if (!openAIResult.contains(property)) {
        return false;
      }
    }
    return true;
  }
}
