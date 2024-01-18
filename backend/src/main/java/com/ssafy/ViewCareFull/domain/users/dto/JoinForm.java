package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import lombok.Getter;

@Getter
public class JoinForm {

  private String id;
  private String password;
  private String name;
  private String phoneNumber;
  private String birth;
  private UserType userType = UserType.Guardian;

  public void changeUserType(UserType userType) {
    this.userType = userType;
  }
}
