package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginForm {

  private String id;
  private String password;

}
