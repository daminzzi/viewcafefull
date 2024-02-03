package com.ssafy.ViewCareFull.domain.conference.dto;

import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceInfo {

  private String applicationId;
  private String targetName;
  private String targetRoom;
  private String permissionId;
  private LocalDateTime createdDatetime;
  private String conferenceDate;
  private String conferenceTime;
  private PermissionType conferenceState;
  private String conferenceLink;
  private LocalDateTime startDatetime;
  private LocalDateTime endDatetime;
}
