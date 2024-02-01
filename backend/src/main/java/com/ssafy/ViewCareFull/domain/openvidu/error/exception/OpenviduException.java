package com.ssafy.ViewCareFull.domain.openvidu.error.exception;

import com.ssafy.ViewCareFull.domain.openvidu.error.OpenviduErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class OpenviduException extends RuntimeException {

  private final OpenviduErrorCode errorCode;
  private String errorMessage;
}
