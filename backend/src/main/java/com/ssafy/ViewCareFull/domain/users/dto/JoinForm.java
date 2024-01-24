package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
