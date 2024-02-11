package com.ssafy.ViewCareFull.domain.report.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.domain.report.entity.Reports;
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

  public static MonthlyReport of(Reports report) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(report.getReportInfo(), MonthlyReport.class);
  }

  public Reports toEntity(RequestReportDto requestReportDto) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return Reports.builder()
        .year(requestReportDto.getMonth()/100)
        .month(requestReportDto.getMonth()%100)
        .caregiverId(requestReportDto.getId())
        .reportInfo(objectMapper.writeValueAsString(this))
        .build();
  }
  public void cntCondition(int year, int month, NumOfConditions numOfConditions) {
    this.year = year;
    this.month = month;
    this.condition = new HealthCondition();
    this.condition.good = numOfConditions.getCntGood();
    this.condition.normal = numOfConditions.getCntNormal();
    this.condition.bad = numOfConditions.getCntBad();
  }

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
  public static class HealthCondition {

    private int good;
    private int normal;
    private int bad;
  }


  public static List<String> properties() {
    return Arrays.asList("pressure", "sugar", "insights");
  }
}
