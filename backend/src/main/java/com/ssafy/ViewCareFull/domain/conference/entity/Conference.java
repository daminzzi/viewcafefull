package com.ssafy.ViewCareFull.domain.conference.entity;

import com.ssafy.ViewCareFull.domain.common.entity.BaseTime;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conference extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id")
  private Guardian guardian;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "target_id")
  private Caregiver caregiver;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id")
  private Hospital hospital;

  @Column(name = "conference_date")
  private LocalDate conferenceDate;

  @Column(name = "conference_time")
  private LocalTime conferenceTime;

  @Column(name = "conference_state")
  @Enumerated(EnumType.STRING)
  private PermissionType conferenceState;

  @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ConferenceReservation> conferenceReservations;

  @Embedded
  private ConferenceRoom conferenceRoom;

  public void addReservationList(UserLink userlink) {
    this.conferenceReservations.add(new ConferenceReservation(this, userlink.getGuardian().getId()));
  }

  public static Conference createConference(LocalDate conferenceDate, LocalTime conferenceTime) {
    return Conference.builder()
        .conferenceDate(conferenceDate)
        .conferenceTime(conferenceTime)
        .conferenceState(PermissionType.S)
        .conferenceReservations(new ArrayList<>())
        .build();
  }

  public void linkApplicationUsers(UserLink userLink) {
    this.guardian = userLink.getGuardian();
    this.caregiver = userLink.getCaregiver();
    this.hospital = userLink.getHospital();
  }

  public void updatePermissionState(String permissionType) {
    this.conferenceState = PermissionType.of(permissionType);
    if (conferenceState == PermissionType.A) {
      this.conferenceRoom = new ConferenceRoom(UUID.randomUUID().toString());
    }
  }

  public void updateStartTime(LocalDateTime localDateTime) {
    this.conferenceRoom.startConference(localDateTime);
  }

  public void updateEndTime(LocalDateTime localDateTime) {
    this.conferenceRoom.endConference(localDateTime);
  }
}
