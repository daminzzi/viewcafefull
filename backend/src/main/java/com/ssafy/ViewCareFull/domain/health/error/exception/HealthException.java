package com.ssafy.ViewCareFull.domain.health.error.exception;

import com.ssafy.ViewCareFull.domain.health.error.HealthErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HealthException extends RuntimeException {

  private final HealthErrorCode errorCode;

}
