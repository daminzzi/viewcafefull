package com.ssafy.ViewCareFull.domain.condition.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ViewCareFull.domain.condition.entity.ConditionType;
import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ConditionRequestDto {

  private String date;
  private String condition;
  @JsonIgnore
  private LocalDate toDate;

  public ConditionRequestDto(String date, String condition) {
    this.date = date;
    this.condition = condition;
    this.toDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  public Conditions toEntity(Users user) {
    return Conditions.builder()
        .user(user)
        .date(toDate)
        .condition(ConditionType.valueOf(condition.toUpperCase()))
        .build();
  }

  @JsonIgnore
  public ConditionType getConditionType() {
    return ConditionType.valueOf(condition.toUpperCase());
  }
}
