package com.ssafy.ViewCareFull.domain.gcp.error.exception;

import com.ssafy.ViewCareFull.domain.gcp.error.GcpErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GcpException extends RuntimeException {

  private final GcpErrorCode errorCode;
  private String errorMessage;
}
