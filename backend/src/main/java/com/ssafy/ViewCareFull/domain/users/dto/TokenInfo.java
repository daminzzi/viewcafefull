package com.ssafy.ViewCareFull.domain.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {

  private String accessToken;
  private String refreshToken;

  @Builder
  public TokenInfo(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
