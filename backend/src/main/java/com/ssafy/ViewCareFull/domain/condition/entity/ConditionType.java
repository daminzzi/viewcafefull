package com.ssafy.ViewCareFull.domain.condition.entity;

public enum ConditionType {
  GOOD, BAD, NORMAL;

  public static ConditionType of(String conditionType) {
    return ConditionType.valueOf(conditionType.toUpperCase());
  }
}
