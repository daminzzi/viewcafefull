package com.ssafy.ViewCareFull.domain.conference.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ConferenceReservation {

  private List<Participant> applicationList;
  private String targetId;
  private String permissionId;
  private String conferenceDate;
  private String conferenceTime;
  @JsonIgnore
  private Set<String> applicationIds;

  public void makeApplicationIds() {
    this.applicationIds = this.applicationList.stream()
        .map(Participant::getApplicationId)
        .collect(Collectors.toSet());
  }

  @Getter
  private static class Participant {

    private String applicationId;
  }
}
