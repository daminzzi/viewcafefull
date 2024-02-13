package com.ssafy.ViewCareFull.domain.report.dto;

import com.ssafy.ViewCareFull.domain.message.dto.MessageDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyHealthInfo {

  private MonthlyBloodSugar bloodSugar;
  private MonthlyBloodPressure bloodPressure;
  private List<String> messages;

  public MonthlyHealthInfo(MonthlyBloodSugar monthlyBloodSugar, MonthlyBloodPressure monthlyBloodPressure) {
    this.bloodSugar = monthlyBloodSugar;
    this.bloodPressure = monthlyBloodPressure;
    this.messages = new ArrayList<>();
  }

  public void addMessageList(List<MessageDto> messageList) {
    for (MessageDto messageDto : messageList) {
      if (messageDto.getTitle().contains("식단") || messageDto.getContent().contains("식단")) {
        continue;
      }
      this.messages.add(messageDto.getContent());
    }
  }
}
