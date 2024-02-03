package com.ssafy.ViewCareFull.domain.users.error;

import com.ssafy.ViewCareFull.domain.users.error.exception.UserLinkNotMatchException;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserTypeException;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UsersHandler {

  @ExceptionHandler(UsersException.class)
  public ResponseEntity<?> usersExceptionHandler(UsersException e) {
    log.error("[handleUsersException] {} : {}", e.getErrorCode().name(), e.getErrorCode().getMessage());
    if (e.getErrorCode() == UserErrorCode.NOT_FOUND_OAUTH_USER) {
      return ResponseEntity.status(e.getErrorCode().getHttpStatus())
          .body(Collections.singletonMap("email", e.getOauthMail()));
    }
    return ResponseEntity.status(e.getErrorCode().getHttpStatus()).build();
  }

  @ExceptionHandler(UserTypeException.class)
  public ResponseEntity<String> userTypeExceptionHandler(UserTypeException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @ExceptionHandler(UserLinkNotMatchException.class)
  public ResponseEntity<String> userLinkNotMatchExceptionHandler(UserLinkNotMatchException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

}
