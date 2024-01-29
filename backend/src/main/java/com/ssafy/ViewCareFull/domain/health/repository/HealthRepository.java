package com.ssafy.ViewCareFull.domain.health.repository;

import com.ssafy.ViewCareFull.domain.health.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
  
}
