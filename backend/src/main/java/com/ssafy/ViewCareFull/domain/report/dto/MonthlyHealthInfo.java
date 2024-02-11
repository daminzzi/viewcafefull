package com.ssafy.ViewCareFull.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthlyHealthInfo {

  private final MonthlyBloodSugar bloodSugar;
  private final MonthlyBloodPressure bloodPressure;

  public MonthlyHealthInfo(MonthlyBloodSugar monthlyBloodSugar, MonthlyBloodPressure monthlyBloodPressure) {
    this.bloodSugar = monthlyBloodSugar;
    this.bloodPressure = monthlyBloodPressure;
  }
}
