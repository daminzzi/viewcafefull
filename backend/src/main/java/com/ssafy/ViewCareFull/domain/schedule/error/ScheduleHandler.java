package com.ssafy.ViewCareFull.domain.schedule.error;

import com.ssafy.ViewCareFull.domain.schedule.error.exception.ScheduleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ScheduleHandler {

  @ExceptionHandler(ScheduleException.class)
  public ResponseEntity<Void> ScheduleExceptionHandler(ScheduleException e) {
    log.error("[ScheduleExceptionHandler] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
