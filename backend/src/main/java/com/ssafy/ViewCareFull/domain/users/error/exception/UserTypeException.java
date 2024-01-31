package com.ssafy.ViewCareFull.domain.users.error.exception;

public class UserTypeException extends RuntimeException {

  public UserTypeException() {
    super("유저 타입이 잘못되었습니다.");
  }
}
