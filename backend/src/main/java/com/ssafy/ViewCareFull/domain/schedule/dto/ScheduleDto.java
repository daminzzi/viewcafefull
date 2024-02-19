package com.ssafy.ViewCareFull.domain.schedule.dto;

import com.ssafy.ViewCareFull.domain.schedule.entity.DayType;
import com.ssafy.ViewCareFull.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

  private String startTime;
  private String endTime;
  private int unit;
  private String day;


  public static ScheduleDto of(Schedule schedule) {
    return ScheduleDto.builder()
        .startTime(schedule.getStartTime().toString())
        .endTime(schedule.getEndTime().toString())
        .unit(schedule.getUnit())
        .day(DayType.getDayTypeToNumber(schedule.getDay()))
        .build();
  }
}
