package com.ssafy.ViewCareFull.domain.conference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceReservationDto {

  private List<Participant> applicationList;
  private String targetId;
  private String permissionId;
  @JsonFormat(pattern = "yyyyMMdd")
  private LocalDate conferenceDate;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime conferenceTime;
  @JsonIgnore
  private Set<String> applicationIds;

  public void makeApplicationIds() {
    this.applicationIds = this.applicationList.stream()
        .map(Participant::getApplicationId)
        .collect(Collectors.toSet());
  }

  public boolean chkApply(Guardian guardian) {
    return this.applicationIds.contains(guardian.getDomainId());
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Participant {

    private String applicationId;
  }
}
