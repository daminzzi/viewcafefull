package com.ssafy.ViewCareFull.domain.report.error;

import com.ssafy.ViewCareFull.domain.report.error.exception.ReportException;
import com.ssafy.ViewCareFull.domain.report.util.RestApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReportExceptionHandler {

  @ExceptionHandler(ReportException.class)
  public ResponseEntity<Void> healthExceptionHandler(ReportException e) {
    log.error("[HealthExceptionHandler] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    if (e.hasThreadInfo()) {
      e.getThreadInfo().deleteThread();
      log.info("[HealthExceptionHandler] delete OpenAI thread");
    }
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }
}
