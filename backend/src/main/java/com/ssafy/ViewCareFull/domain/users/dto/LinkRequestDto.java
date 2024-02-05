package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LinkRequestDto {

  private String targetCode;
  private String relationship;

}
