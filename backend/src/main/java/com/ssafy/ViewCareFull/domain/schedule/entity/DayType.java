package com.ssafy.ViewCareFull.domain.schedule.entity;

import com.ssafy.ViewCareFull.domain.schedule.error.ScheduleErrorCode;
import com.ssafy.ViewCareFull.domain.schedule.error.exception.ScheduleException;

public enum DayType {
  MON, TUE, WED, THU, FRI, SAT, SUN;

  public static String getNumberDayType(DayType dayType) throws ScheduleException {
    switch (dayType) {
      case MON:
        return "1";
      case TUE:
        return "2";
      case WED:
        return "3";
      case THU:
        return "4";
      case FRI:
        return "5";
      case SAT:
        return "6";
      case SUN:
        return "0";
      default:
        throw new ScheduleException(ScheduleErrorCode.NOT_FOUND_DAY_TYPE);
    }
  }
}
