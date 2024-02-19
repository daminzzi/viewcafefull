package com.ssafy.ViewCareFull.domain.conference.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConferenceInfoSummaryDto {

  private Integer reservedConference;
  private List<ConferenceInfo> todayConferenceList;

  public ConferenceInfoSummaryDto(int reservedConference, List<ConferenceInfo> todayConferenceList) {
    this.reservedConference = reservedConference;
    this.todayConferenceList = todayConferenceList;
  }
}
