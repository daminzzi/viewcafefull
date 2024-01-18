package com.ssafy.ViewCareFull.domain.users.error.exception;

import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsersException extends RuntimeException {

  private final UserErrorCode errorCode;

}
