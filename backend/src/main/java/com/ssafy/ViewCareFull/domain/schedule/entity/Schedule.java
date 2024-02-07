package com.ssafy.ViewCareFull.domain.schedule.entity;

import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
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
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Hospital hospital;

  @NotNull
  @Column
  private LocalTime startTime;

  @NotNull
  @Column
  private LocalTime endTime;

  @NotNull
  @Column
  private Integer unit;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "day_type")
  private DayType day;

}
