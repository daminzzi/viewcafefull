package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.common.entity.TimeType;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import lombok.Getter;

@Getter
public class HealthTimeLevelDto {

  private int level;
  private String time;

  public HealthTimeLevelDto(Health health) {
    this.level = health.getLevel();
    this.time = TimeType.getKorean(health.getTime());
  }
}
