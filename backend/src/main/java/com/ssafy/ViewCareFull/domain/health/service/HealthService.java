package com.ssafy.ViewCareFull.domain.health.service;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyHealthInfo;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import java.time.LocalDate;
import java.util.List;

public interface HealthService {

  void saveHealthInfo(String domainId, HealthInfo healthInfo);

  void deleteHealthInfo(String id);

  void updateHealthInfo(String id, HealthInfo healthInfo);

  List<Health> getHealthListWithIdDate(String domainId, LocalDate date);

  MonthlyHealthInfo getMonthlyAverageHealthList(Long id, LocalDate start, LocalDate end);
}
