package com.ssafy.ViewCareFull.domain.schedule.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode {
  SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
  NOT_FOUND_DAY_TYPE(HttpStatus.BAD_REQUEST, "요일을 잘못 입력하였습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
