package com.ssafy.ViewCareFull.domain.users.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

  DUPLICATED_ID(HttpStatus.CONFLICT, "중복된 아이디입니다."),
  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
  NOT_MATCHING_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
  NOT_FOUND_USERID(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
  NOT_MATCHING_USERTYPE(HttpStatus.NOT_FOUND, "가입된 아이디는 있으나 찾고자하는 유저 타입이 아닙니다."),
  NOT_FOUND_OAUTH_USER(HttpStatus.BAD_REQUEST, "외부서비스로 가입된 유저가 아닙니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다.");

  private final HttpStatus httpStatus;
  private final String message;

}
