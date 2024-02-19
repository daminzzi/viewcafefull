package com.ssafy.ViewCareFull.domain.common.entity;

public enum TimeType {
  MORNING, NOON, DINNER;

  public static TimeType matchTimeType(String timeType) {
    switch (timeType) {
      case "morning":
        return MORNING;
      case "noon":
        return NOON;
      case "dinner":
        return DINNER;
      default:
        throw new IllegalArgumentException("Invalid TimeType");
    }
  }

  public static String getKorean(TimeType timeType) {
    switch (timeType) {
      case MORNING:
        return "아침";
      case NOON:
        return "점심";
      case DINNER:
        return "저녁";
      default:
        throw new IllegalArgumentException("Invalid TimeType");
    }
  }

  public static TimeType getFromKorean(String timeType) {
    switch (timeType) {
      case "아침":
        return MORNING;
      case "점심":
        return NOON;
      case "저녁":
        return DINNER;
      default:
        throw new IllegalArgumentException("Invalid TimeType");
    }
  }
}
