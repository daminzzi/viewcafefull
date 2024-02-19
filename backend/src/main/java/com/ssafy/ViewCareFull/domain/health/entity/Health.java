package com.ssafy.ViewCareFull.domain.health.entity;

import com.ssafy.ViewCareFull.domain.common.entity.TimeType;
import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  public Health(Users user, HealthInfo healthInfo) {
    this.user = user;
    this.level = healthInfo.getLevel();
    this.healthType = HealthType.matchHealthType(healthInfo.getHealthType());
    this.healthDate = LocalDate.parse(healthInfo.getHealthDate());
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;

  @NotNull
  @Column(name = "level")
  private Integer level;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "health_type")
  private HealthType healthType;

  @Enumerated(EnumType.STRING)
  @Column(name = "time")
  private TimeType time;

  @NotNull
  @Column(name = "health_date")
  private LocalDate healthDate;

  public void update(HealthInfo healthInfo) {
    if (healthInfo.getLevel() != null) {
      this.level = healthInfo.getLevel();
    }
    if (healthInfo.getHealthDate() != null) {
      this.healthDate = LocalDate.parse(healthInfo.getHealthDate());
    }
    if (healthInfo.getHealthType() != null) {
      this.healthType = HealthType.matchHealthType(healthInfo.getHealthType());
    }
  }
}
