package com.ssafy.ViewCareFull.domain.users.error.exception;

import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersException extends RuntimeException {

  private final UserErrorCode errorCode;
  private String oauthMail;
  
}
