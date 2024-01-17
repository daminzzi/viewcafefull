package com.ssafy.ViewCareFull.domain.conference.entity;

import com.ssafy.ViewCareFull.domain.user.entity.PermissionType;
import com.ssafy.ViewCareFull.domain.user.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.user.entity.user.Hospital;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Entity
@Getter
public class Conference {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id")
  private Guardian guardian;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  private Hospital hospital;

  @Column(name = "created_datetime")
  private LocalDateTime createdDateTime;

  @Column(name = "conference_date")
  private LocalDate conferenceDate;

  @Column(name = "conference_time")
  private LocalTime conferenceTime;

  @Column(name = "conference_state")
  @Enumerated(EnumType.STRING)
  private PermissionType conferenceState;

  @Column(name = "start_datetime")
  private LocalDateTime startDateTime;

  @Column(name = "end_datetime")
  private LocalDateTime endDateTime;
}
