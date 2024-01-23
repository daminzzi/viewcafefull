package com.ssafy.ViewCareFull.domain.users.dto;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

  private String name;
  private String accessToken;
  private String refreshToken;
  private String role;

  @Builder
  public LoginResponse(Users user, TokenInfo tokenInfo) {
    this.name = user.getDomainId();
    this.accessToken = tokenInfo.getAccessToken();
    this.refreshToken = tokenInfo.getRefreshToken();
    this.role = user.getUserType();
  }

  public void removeRefreshToken() {
    this.refreshToken = null;
  }
}
