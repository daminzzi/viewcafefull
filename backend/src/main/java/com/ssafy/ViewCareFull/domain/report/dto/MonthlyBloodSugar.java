package com.ssafy.ViewCareFull.domain.report.dto;

import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.entity.HealthType;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
public class MonthlyBloodSugar implements AverageCalculator {

  private int[] averageAfterMeal;
  private int[] averageBeforeMeal;
  @JsonIgnore
  private int[] averageAfterMealCnt;
  @JsonIgnore
  private int[] averageBeforeMealCnt;

  public MonthlyBloodSugar(int days) {
    this.averageAfterMeal = new int[days];
    this.averageBeforeMeal = new int[days];
    this.averageAfterMealCnt = new int[days];
    this.averageBeforeMealCnt = new int[days];
  }

  @Override
  public void add(Health health) {
    if (health.getHealthType() == HealthType.B) {
      averageBeforeMeal[health.getHealthDate().getDayOfMonth() - 1] += health.getLevel();
      averageBeforeMealCnt[health.getHealthDate().getDayOfMonth() - 1]++;
    } else {
      averageAfterMeal[health.getHealthDate().getDayOfMonth() - 1] += health.getLevel();
      averageAfterMealCnt[health.getHealthDate().getDayOfMonth() - 1]++;
    }
  }

  @Override
  public void averge(int days) {
    for (int i = 0; i < days; i++) {
      if (averageAfterMealCnt[i] != 0) {
        averageAfterMeal[i] /= averageAfterMealCnt[i];
      }
      if (averageBeforeMealCnt[i] != 0) {
        averageBeforeMeal[i] /= averageBeforeMealCnt[i];
      }
    }
  }
}
