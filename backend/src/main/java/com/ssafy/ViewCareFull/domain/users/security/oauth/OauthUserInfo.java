package com.ssafy.ViewCareFull.domain.users.security.oauth;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OauthUserInfo {

  private final Map<String, Object> ouathAttributes;

  public String getMail() {
    return (String) ouathAttributes.get("email");
  }
}
