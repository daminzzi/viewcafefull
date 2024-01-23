package com.ssafy.ViewCareFull.domain.users.error;

import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UsersHandler {

  @ExceptionHandler(UsersException.class)
  public ResponseEntity<Void> UsersExceptionHandler(UsersException e) {
    log.error("[handleUsersException] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }

}
