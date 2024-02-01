package com.ssafy.ViewCareFull.domain.gcp.error;

import com.ssafy.ViewCareFull.domain.gcp.error.exception.GcpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GcpHandler {

  @ExceptionHandler(GcpException.class)
  public ResponseEntity<?> GcpExceptionHandler(GcpException e) {
    log.error("[handleGcpException] {} : {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage(),
        e.getErrorMessage());
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }
}
