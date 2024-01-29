package com.ssafy.ViewCareFull.domain.health.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HealthErrorCode {

  NOT_FOUND_HEALTH_TYPE(HttpStatus.NOT_FOUND, "해당하는 건강 타입이 없습니다."),
  NOT_FOUND_HEALTH_ID(HttpStatus.FORBIDDEN, "해당하는 건강 정보가 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
