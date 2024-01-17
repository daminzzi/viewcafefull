package com.ssafy.ViewCareFull.domain.health.entity;

import com.ssafy.ViewCareFull.domain.user.entity.user.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class Health {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users userId;

  @NotNull
  private Integer level;

  @NotNull
  @Enumerated(EnumType.STRING)
  private HealthType healthType;

  @NotNull
  private LocalDate healthDate;
}
