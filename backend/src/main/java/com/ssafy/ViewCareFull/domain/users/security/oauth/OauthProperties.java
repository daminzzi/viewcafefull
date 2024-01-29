package com.ssafy.ViewCareFull.domain.users.security.oauth;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {

  private final Map<String, Client> client = new HashMap<>();

  @Getter
  @Setter
  public static class Client {

    private String clientId;
    private String redirectUri;
  }

  private final Map<String, Provider> provider = new HashMap<>();

  @Getter
  @Setter
  public static class Provider {

    private String tokenUri;
    private String userInfoUri;
    private String userNameAttribute;
  }
}