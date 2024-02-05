package com.ssafy.ViewCareFull.domain.conference.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceTodayListDto extends ConferenceInfoListDto {

  private List<TodayConferenceInfo> todayConferenceList;

  public ConferenceTodayListDto(ConferenceInfoListDto conferenceList, List<TodayConferenceInfo> todayConferenceList) {
    super(conferenceList.getConferenceList());
    this.todayConferenceList = todayConferenceList;
  }
}
