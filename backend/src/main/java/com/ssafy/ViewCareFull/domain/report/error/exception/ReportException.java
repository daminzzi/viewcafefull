package com.ssafy.ViewCareFull.domain.report.error.exception;

import com.ssafy.ViewCareFull.domain.report.dto.OpenAI.ThreadInfo;
import com.ssafy.ViewCareFull.domain.report.error.ReportErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReportException extends RuntimeException {

  private final ReportErrorCode errorCode;
  private ThreadInfo threadInfo;

  public boolean hasThreadInfo() {
    return threadInfo != null;
  }
}
