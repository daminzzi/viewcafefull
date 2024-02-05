package com.ssafy.ViewCareFull.domain.conference.dto;

import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ConferenceInfoSummaryDto {

  private Integer reservedConference;
  private List<TodayConferenceInfo> todayConferenceList;

  public ConferenceInfoSummaryDto(Integer reservedConference, List<TodayConferenceInfo> todayConferenceList) {
    this.reservedConference = reservedConference;
    this.todayConferenceList = todayConferenceList.stream()
        .sorted(Comparator.comparing(TodayConferenceInfo::getConferenceTime)).toList();
  }
}
