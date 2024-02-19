package com.ssafy.ViewCareFull.domain.conference.error.exception;

import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConferenceException extends RuntimeException {

  private final ConferenceErrorCode errorCode;
}
