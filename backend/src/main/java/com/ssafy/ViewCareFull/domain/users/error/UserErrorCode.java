package com.ssafy.ViewCareFull.domain.users.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

  DUPLICATED_ID(HttpStatus.CONFLICT, "중복된 아이디입니다."),
  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
  NOT_FOUND_USERID(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
  NOT_FOUND_OAUTH_USER(HttpStatus.NOT_FOUND, "외부서비스로 가입된 유저가 아닙니다.");

  private final HttpStatus httpStatus;
  private final String message;

}
