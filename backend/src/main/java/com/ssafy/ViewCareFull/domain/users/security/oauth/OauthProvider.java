package com.ssafy.ViewCareFull.domain.users.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OauthProvider {

  private final String clientId;
  private final String redirectUrl;
  private final String tokenUrl;
  private final String userInfoUrl;

  public OauthProvider(OauthProperties.Client client, OauthProperties.Provider provider) {
    this(client.getClientId(), client.getRedirectUri(), provider.getTokenUri(),
        provider.getUserInfoUri());
  }

}
