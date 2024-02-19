package com.ssafy.ViewCareFull.domain.users.error.exception;

public class UserLinkNotMatchException extends RuntimeException {

  public UserLinkNotMatchException() {
    super("일치하는 관계 정보가 없습니다.");
  }

}
