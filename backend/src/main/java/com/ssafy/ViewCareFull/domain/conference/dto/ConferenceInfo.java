package com.ssafy.ViewCareFull.domain.conference.dto;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceInfo {

  private long conferenceId;
  private String applicationId;
  private String targetName;
  private String permissionId;
  private LocalDateTime createdDatetime;
  private String conferenceDate;
  private String conferenceTime;
  private PermissionType conferenceState;
  private String sessionName;
  private LocalDateTime startDatetime;
  private LocalDateTime endDatetime;

  public static ConferenceInfo of(Conference conference) {
    ConferenceInfo.ConferenceInfoBuilder conferenceInfoBuilder = ConferenceInfo.builder()
        .conferenceId(conference.getId())
        .applicationId(conference.getGuardian().getDomainId())
        .targetName(conference.getCaregiver().getUserName())
        .permissionId(conference.getHospital().getDomainId())
        .createdDatetime(conference.getCreatedDateTime())
        .conferenceDate(conference.getConferenceDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
        .conferenceTime(conference.getConferenceTime().format(DateTimeFormatter.ofPattern("HH:mm")))
        .conferenceState(conference.getConferenceState());

    if (conference.getConferenceState() == PermissionType.A && conference.getConferenceRoom() != null) {
      conferenceInfoBuilder.sessionName(conference.getConferenceRoom().getRoomName())
          .startDatetime(conference.getConferenceRoom().getStartDateTime())
          .endDatetime(conference.getConferenceRoom().getEndDateTime());
    }

    return conferenceInfoBuilder.build();
  }

  public static List<ConferenceInfo> toList(List<Conference> conferenceList) {
    return conferenceList.stream().map(ConferenceInfo::of).toList();
  }

}
