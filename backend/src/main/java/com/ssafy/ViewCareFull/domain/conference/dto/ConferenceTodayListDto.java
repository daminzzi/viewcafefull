package com.ssafy.ViewCareFull.domain.conference.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceTodayListDto extends ConferenceInfoPageDto {

  private List<ConferenceInfo> todayConferenceList;

  public ConferenceTodayListDto(List<ConferenceInfo> reservedConferenceList, List<ConferenceInfo> todayConferenceList) {
    super(reservedConferenceList);
    this.todayConferenceList = todayConferenceList;
  }
}
