package com.ssafy.ViewCareFull.domain.gallery.exception;

public class NoMatchCaregiverException extends RuntimeException {

  public NoMatchCaregiverException() {
    super("연결된 입소자가 없습니다");
  }
}
