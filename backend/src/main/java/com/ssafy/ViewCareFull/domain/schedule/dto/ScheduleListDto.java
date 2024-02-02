package com.ssafy.ViewCareFull.domain.schedule.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleListDto {

  private List<ScheduleDto> scheduleList;

}
