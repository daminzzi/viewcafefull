package com.ssafy.ViewCareFull.domain.report.dto;

import com.ssafy.ViewCareFull.domain.health.entity.Health;

public interface AverageCalculator {

  void add(Health health);

  void averge(int days);

}
