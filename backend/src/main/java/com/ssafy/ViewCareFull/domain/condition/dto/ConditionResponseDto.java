package com.ssafy.ViewCareFull.domain.condition.dto;

import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ConditionResponseDto {

  private String date;
  private String data;

  public ConditionResponseDto(LocalDate date) {
    this.date = date.toString();
  }

  public ConditionResponseDto updateData(Conditions conditions) {
    String text = conditions.getCondition().toString();
    if (text.equals("GOOD")) {
      this.data = "좋음";
    }
    if (text.equals("NORMAL")) {
      this.data = "보통";
    }
    if (text.equals("BAD")) {
      this.data = "나쁨";
    }
    return this;
  }
}
