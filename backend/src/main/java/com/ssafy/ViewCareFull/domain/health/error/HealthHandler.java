package com.ssafy.ViewCareFull.domain.health.error;

import com.ssafy.ViewCareFull.domain.health.error.exception.HealthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HealthHandler {


  @ExceptionHandler(HealthException.class)
  public ResponseEntity<Void> healthExceptionHandler(HealthException e) {
    log.error("[HealthExceptionHandler] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }
}
