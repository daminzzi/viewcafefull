package com.ssafy.ViewCareFull.domain.condition.dto;

import lombok.Getter;

@Getter
public class ConditionDataDto {

  private String morning;
  private String noon;
  private String dinner;

  public void updateMorning(String string) {
    this.morning = string;
  }

  public void updateNoon(String string) {
    this.noon = string;
  }

  public void updateDinner(String string) {
    this.dinner = string;
  }
}
