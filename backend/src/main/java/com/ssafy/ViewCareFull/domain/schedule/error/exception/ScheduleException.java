package com.ssafy.ViewCareFull.domain.schedule.error.exception;

import com.ssafy.ViewCareFull.domain.schedule.error.ScheduleErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleException extends RuntimeException {

  private final ScheduleErrorCode errorCode;
}
