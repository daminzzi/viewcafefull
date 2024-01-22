package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {

  private String grantType;
  private String accessToken;
  private String refreshToken;

  @Builder
  public TokenInfo(String grantType, String accessToken, String refreshToken) {
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
