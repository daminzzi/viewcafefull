package com.ssafy.ViewCareFull.domain.openvidu.error;

import com.ssafy.ViewCareFull.domain.openvidu.error.exception.OpenviduException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OpenviduHandler {

  @ExceptionHandler(OpenviduException.class)
  public ResponseEntity<?> OpenviduExceptionHandler(OpenviduException e) {
    log.error("[handleGcpException] {} : {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage(),
        e.getErrorMessage());
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }
}
