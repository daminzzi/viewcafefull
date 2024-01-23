package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

  private String id;
  private String name;
  private String phoneNumber;
  private String birth;
  private String role;

}
