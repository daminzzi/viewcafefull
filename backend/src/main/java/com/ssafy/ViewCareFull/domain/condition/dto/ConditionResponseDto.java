package com.ssafy.ViewCareFull.domain.condition.dto;

import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import lombok.Getter;

@Getter
public class ConditionResponseDto {

  private String date;
  private ConditionDataDto data;

  public ConditionResponseDto(Conditions conditions) {
    this.date = conditions.getDate().toString();
    this.data = new ConditionDataDto();
    updateData(conditions);
  }

  public void updateData(Conditions conditions) {
    String text = conditions.getCondition().toString();
    if (text.equals("GOOD")) {
      text = "좋음";
    }
    if (text.equals("NORMAL")) {
      text = "보통";
    }
    if (text.equals("BAD")) {
      text = "나쁨";
    }
    switch (conditions.getTime()) {
      case MORNING:
        this.data.updateMorning(text);
        break;
      case NOON:
        this.data.updateNoon(text);
        break;
      case DINNER:
        this.data.updateDinner(text);
        break;
    }
  }
}
