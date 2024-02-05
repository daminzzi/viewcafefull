package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginForm {

  private String id;
  private String password;

}
