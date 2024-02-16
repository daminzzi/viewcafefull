package com.ssafy.ViewCareFull.domain.report.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportMessageDto {

  private int year;
  private int month;
  private Long targetId;
  private String targetName;


  public String toJson() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(this);
    } catch (Exception e) {
      throw new RuntimeException("JSON 변환 중 에러가 발생했습니다.");
    }
  }
}
