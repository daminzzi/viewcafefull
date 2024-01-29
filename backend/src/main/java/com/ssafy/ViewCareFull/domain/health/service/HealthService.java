package com.ssafy.ViewCareFull.domain.health.service;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;

public interface HealthService {

  void saveHealthInfo(String domainId, HealthInfo healthInfo);

  void deleteHealthInfo(String id);
}
