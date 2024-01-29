package com.ssafy.ViewCareFull.domain.users.security.oauth;

import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OauthAttributes {
  KAKAO("kakao") {
    @Override
    public OauthUserInfo of(Map<String, Object> attributes) {
      Map<String, Object> kakaoAttributes = (Map<String, Object>) attributes.get("kakao_account");
      return OauthUserInfo.builder()
          .ouathAttributes(kakaoAttributes)
          .build();
    }
  };

  private final String providerName;

  public static OauthUserInfo extract(String providerName, Map<String, Object> attributes) {
    return Arrays.stream(values())
        .filter(provider -> providerName.equals(provider.providerName))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new)
        .of(attributes);
  }

  public abstract OauthUserInfo of(Map<String, Object> attributes);
}
