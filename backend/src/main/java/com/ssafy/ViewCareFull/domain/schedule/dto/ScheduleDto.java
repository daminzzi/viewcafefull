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


  public ScheduleDto(Schedule schedule) {
    this.startTime = schedule.getStartTime().toString();
    this.endTime = schedule.getEndTime().toString();
    this.unit = schedule.getUnit();
    this.day = DayType.getNumberDayType(schedule.getDay());
  }
}
