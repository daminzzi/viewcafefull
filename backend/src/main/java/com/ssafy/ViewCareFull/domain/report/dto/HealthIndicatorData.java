package com.ssafy.ViewCareFull.domain.report.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthIndicatorData {

  private BloodPressure bloodPressure;
  private BloodSugar bloodSugar;
  private AdditionalContext additionalContext;
  private String analysisPeriod;

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BloodPressure {

    private List<Integer> systolic;
    private List<Integer> diastolic;

  }

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BloodSugar {

    private List<Integer> fasting;
    private List<Integer> postprandial;

  }

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AdditionalContext {

    private int age;
    private String gender;

  }
}
