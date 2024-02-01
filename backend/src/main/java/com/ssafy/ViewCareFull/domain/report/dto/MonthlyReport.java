package com.ssafy.ViewCareFull.domain.report.dto;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReport {

  private int year;
  private int month;
  private String lifeInfo;
  private String movie;
  private String message;
  private PressureMetrics pressure;
  private SugarMetrics sugar;
  private HealthCondition condition;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PressureMetrics {

    private Insights insights;
    private List<HealthData> data;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SugarMetrics {

    private Insights insights;
    private List<HealthData> data;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Insights {

    private String early;
    private String mid;
    private String late;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class HealthData {

    private String day;
    private int low;
    private int high;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Condition {

    private Insights insights;

  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class HealthCondition {

    private int good;
    private int normal;
    private int bad;
  }


  public static List<String> properties() {
    return Arrays.asList("pressure", "sugar", "insights");
  }
}
