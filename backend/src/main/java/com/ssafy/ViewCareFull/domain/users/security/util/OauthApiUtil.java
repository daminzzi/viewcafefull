package com.ssafy.ViewCareFull.domain.users.security.util;

import com.ssafy.ViewCareFull.domain.users.dto.OauthTokenResponse;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthAttributes;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthProvider;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthUserInfo;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

public class OauthApiUtil {

  public static OauthTokenResponse getAccessToken(String code, OauthProvider provider) {
    return WebClient.create()
        .post()
        .uri(provider.getTokenUrl())
        .headers(header -> {
          header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
          header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
          header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        })
        .bodyValue(tokenRequest(code, provider))
        .retrieve()
        .bodyToMono(OauthTokenResponse.class)
        .block();
  }

  public static MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", code);
    formData.add("grant_type", "authorization_code");
    formData.add("redirect_uri", provider.getRedirectUrl());
    formData.add("client_id", provider.getClientId());
    return formData;
  }

  public static OauthUserInfo getOauthUserInfo(String providerName, OauthTokenResponse tokenResponse,
      OauthProvider provider) {
    Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
    return OauthAttributes.extract(providerName, userAttributes);
  }

  public static Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
    return WebClient.create()
        .get()
        .uri(provider.getUserInfoUrl())
        .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        })
        .block();
  }
}
