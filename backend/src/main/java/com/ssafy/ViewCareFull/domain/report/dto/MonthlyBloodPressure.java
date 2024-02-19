package com.ssafy.ViewCareFull.domain.report.dto;

import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.entity.HealthType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@AllArgsConstructor
public class MonthlyBloodPressure implements AverageCalculator {

  private int[] averageSystolic;
  private int[] averageDiastolic;
  @JsonIgnore
  private int[] averageSystolicCnt;
  @JsonIgnore
  private int[] averageDiastolicCnt;

  public MonthlyBloodPressure(int days) {
    this.averageSystolic = new int[days];
    this.averageDiastolic = new int[days];
    this.averageSystolicCnt = new int[days];
    this.averageDiastolicCnt = new int[days];
  }

  @Override
  public void add(Health health) {
    if (health.getHealthType() == HealthType.H) {
      averageSystolic[health.getHealthDate().getDayOfMonth() - 1] += health.getLevel();
      averageSystolicCnt[health.getHealthDate().getDayOfMonth() - 1]++;
    } else {
      averageDiastolic[health.getHealthDate().getDayOfMonth() - 1] += health.getLevel();
      averageDiastolicCnt[health.getHealthDate().getDayOfMonth() - 1]++;
    }
  }

  @Override
  public void averge(int days) {
    for (int i = 0; i < days; i++) {
      if (averageSystolicCnt[i] != 0) {
        averageSystolic[i] /= averageSystolicCnt[i];
      }
      if (averageDiastolicCnt[i] != 0) {
        averageDiastolic[i] /= averageDiastolicCnt[i];
      }
    }
  }
}
