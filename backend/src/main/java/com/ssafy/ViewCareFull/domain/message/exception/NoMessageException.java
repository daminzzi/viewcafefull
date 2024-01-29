package com.ssafy.ViewCareFull.domain.message.exception;

public class NoMessageException extends RuntimeException {

  public NoMessageException() {
    super("메시지가 없습니다.");
  }

}
