package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import lombok.Getter;

@Getter
public class CaregiverIdDto {

  private Long caregiverId;

  public CaregiverIdDto(UserLink userLink) {
    this.caregiverId = userLink.getCaregiver().getId();
  }
}
