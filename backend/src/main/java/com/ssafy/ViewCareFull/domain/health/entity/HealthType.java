package com.ssafy.ViewCareFull.domain.health.entity;

import com.ssafy.ViewCareFull.domain.health.error.HealthErrorCode;
import com.ssafy.ViewCareFull.domain.health.error.exception.HealthException;

/**
 * B : 식전 혈당 A : 식후 혈당 L : 낮은 혈압 H : 높은 혈압
 */
public enum HealthType {
  B, A, L, H;

  public static HealthType matchHealthType(String healthType) {
    switch (healthType) {
      case "B":
        return HealthType.B;
      case "A":
        return HealthType.A;
      case "L":
        return HealthType.L;
      case "H":
        return HealthType.H;
      default:
        throw new HealthException(HealthErrorCode.NOT_FOUND_HEALTH_TYPE);
    }
  }
}
