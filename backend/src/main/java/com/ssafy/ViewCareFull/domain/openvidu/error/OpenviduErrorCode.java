package com.ssafy.ViewCareFull.domain.openvidu.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OpenviduErrorCode {

  SESSION_IS_NULL(HttpStatus.NOT_FOUND, "session is null");

  private final HttpStatus httpStatus;
  private final String message;

}
