package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.health.entity.Health;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MainHealthDto {

  private String healthType;
  private List<HealthTimeLevelDto> data = new ArrayList<>();

  public MainHealthDto(Health health) {
    this.healthType = health.getHealthType().toString();
    this.data.add(new HealthTimeLevelDto(health));
  }

  public static List<MainHealthDto> listOf(List<Health> healthInfo) {
    return healthInfo.stream()
        .map(MainHealthDto::new)
        .toList();
  }

  public void addData(Health health) {
    this.data.add(new HealthTimeLevelDto(health));
  }
}
