package com.ssafy.ViewCareFull.domain.conference.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ConferenceErrorCode {
  INVALID_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 타입입니다."),
  NOT_FOUND_CONFERENCE(HttpStatus.NOT_FOUND, "존재하지 않는 면회입니다.");
  private final HttpStatus httpStatus;
  private final String message;
}
