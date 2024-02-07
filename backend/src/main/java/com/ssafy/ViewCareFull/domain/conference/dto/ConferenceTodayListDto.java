package com.ssafy.ViewCareFull.domain.conference.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceTodayListDto extends ConferenceInfoPageDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ConferenceInfo> todayConferenceList;

  public ConferenceTodayListDto(List<ConferenceInfo> reservedConferenceList, List<ConferenceInfo> todayConferenceList) {
    super(reservedConferenceList);
    this.todayConferenceList = todayConferenceList;
  }
}
