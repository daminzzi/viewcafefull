package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinForm {

  private final String id;
  private final String password;
  private final String name;
  private final String phoneNumber;
  private final String birth;
  private String email;
  private UserType userType;

  @Builder
  public JoinForm(String id, String password, String name, String phoneNumber, String birth, String email) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.birth = birth;
    this.email = email;
    this.userType = UserType.Guardian;
  }

  public void changeUserType(UserType userType) {
    this.userType = userType;
  }
}
