package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

  private UserInfo user;
  private String accessToken;
  private String refreshToken;

  @Builder
  public LoginResponse(Users user, TokenInfo tokenInfo) {
    this.user = UserInfo.builder().
        id(user.getDomainId())
        .name(user.getUserName())
        .birth(user.getBrith())
        .phoneNumber(user.getPhoneNumber())
        .role(user.getUserType())
        .build();
    this.accessToken = tokenInfo.getAccessToken();
    this.refreshToken = tokenInfo.getRefreshToken();
    this.role = user.getUserType();
  }

  public void removeRefreshToken() {
    this.refreshToken = null;
  }
}
