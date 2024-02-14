package com.ssafy.ViewCareFull.domain.report.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode {
  NOT_MATCHED_JSON_FORMAT(HttpStatus.BAD_REQUEST, "JSON 형식이 맞지 않습니다."),
  CREATE_REPORT_NOT_YET(HttpStatus.BAD_REQUEST, "건강 정보 분석중입니다..."),
  NOT_FOUND_CREATED_MOVIE(HttpStatus.NOT_FOUND, "만들어진 영상이 없습니다."),
  NOT_FOUND_CREATED_REPORT(HttpStatus.NOT_FOUND, "만들어진 월간 리포트가 없습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
