package com.ssafy.ViewCareFull.domain.users.security.oauth;

import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OauthAdapter {

  public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
    Map<String, OauthProvider> oauthProvider = new HashMap<>();

    properties.getClient().forEach((key, value) -> oauthProvider.put(key,
        new OauthProvider(value, properties.getProvider().get(key))));
    return oauthProvider;
  }
}
