package com.ssafy.ViewCareFull.domain.conference.dto;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayConferenceInfo {

  private String targetName;
  private LocalDate conferenceDate;
  private LocalTime conferenceTime;
  private String conferenceState;
  private LocalDateTime startDatetime;
  private LocalDateTime endDatetime;

  public TodayConferenceInfo(Conference conference) {
    this.targetName = conference.getCaregiver().getUserName();
    this.conferenceDate = conference.getConferenceDate();
    this.conferenceTime = conference.getConferenceTime();
    if (conference.getConferenceRoom() != null) {
      this.startDatetime = conference.getConferenceRoom().getStartDateTime();
      this.endDatetime = conference.getConferenceRoom().getEndDateTime();
    }
    this.conferenceState = updateConferenceState(LocalDateTime.of(conferenceDate, conferenceTime), startDatetime,
        endDatetime);
  }

  public static String updateConferenceState(LocalDateTime conferenceDatetime,
      LocalDateTime startDatetime, LocalDateTime endDatetime) {
    if (startDatetime == null || conferenceDatetime.isAfter(LocalDateTime.now())) {
      return "예정";
    } else if (startDatetime != null && endDatetime == null) {
      return "진행중";
    } else {
      return "완료";
    }
  }
}
