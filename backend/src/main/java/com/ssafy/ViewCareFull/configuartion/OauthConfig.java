package com.ssafy.ViewCareFull.configuartion;


import com.ssafy.ViewCareFull.domain.users.security.oauth.InMemoryProviderRepository;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthAdapter;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthProperties;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
@RequiredArgsConstructor
public class OauthConfig {

  private final OauthProperties properties;

  @Bean
  public InMemoryProviderRepository inMemoryProviderRepository() {
    Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
    return new InMemoryProviderRepository(providers);
  }

}

