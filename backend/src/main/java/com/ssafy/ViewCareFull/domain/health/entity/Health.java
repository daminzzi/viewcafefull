package com.ssafy.ViewCareFull.domain.health.entity;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Health {

  public Health(String domainId, HealthInfo healthInfo) {
    this.userId = domainId;
    this.level = healthInfo.getLevel();
    this.healthType = HealthType.matchHealthType(healthInfo.getHealthType());
    this.healthDate = LocalDate.parse(healthInfo.getHealthDate());
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "user_id")
  private String userId;

  @NotNull
  @Column(name = "level")
  private Integer level;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "health_type")
  private HealthType healthType;

  @NotNull
  @Column(name = "health_date")
  private LocalDate healthDate;

}
