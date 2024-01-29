package com.ssafy.ViewCareFull.domain.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthInfo {

  private String healthType;
  private Integer level;
  private String healthDate;

}
