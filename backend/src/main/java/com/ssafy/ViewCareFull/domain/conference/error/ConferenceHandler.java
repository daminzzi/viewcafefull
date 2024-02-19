package com.ssafy.ViewCareFull.domain.conference.error;

import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ConferenceHandler {

  @ExceptionHandler(ConferenceException.class)
  public ResponseEntity<Void> conferenceExceptionHandler(ConferenceException e) {
    log.error("[conferenceExceptionHandler] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
