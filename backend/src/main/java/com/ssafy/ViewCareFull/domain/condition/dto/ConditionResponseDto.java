package com.ssafy.ViewCareFull.domain.condition.dto;

import com.ssafy.ViewCareFull.domain.condition.entity.Condition;
import lombok.Getter;

@Getter
public class ConditionResponseDto {

  private String date;
  private ConditionDataDto data;

  public ConditionResponseDto(Condition condition) {
    this.date = condition.getDate().toString();
    this.data = new ConditionDataDto();
    updateData(condition);
  }

  public void updateData(Condition condition) {
    String text = condition.getCondition().toString();
    if (text.equals("GOOD")) {
      text = "좋음";
    }
    if (text.equals("NORMAL")) {
      text = "보통";
    }
    if (text.equals("BAD")) {
      text = "나쁨";
    }
    switch (condition.getTime()) {
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
