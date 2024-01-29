package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.LoginResponse;
import com.ssafy.ViewCareFull.domain.users.dto.OauthTokenResponse;
import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.oauth.InMemoryProviderRepository;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthProvider;
import com.ssafy.ViewCareFull.domain.users.security.oauth.OauthUserInfo;
import com.ssafy.ViewCareFull.domain.users.security.util.JwtTokenUtil;
import com.ssafy.ViewCareFull.domain.users.security.util.OauthApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuthUserService {

  private final InMemoryProviderRepository inMemoryProviderRepository;
  private final UsersRepository usersRepository;
  private final JwtTokenUtil jwtTokenUtil;

  public LoginResponse login(String providerName, String code) {
    OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

    OauthTokenResponse tokenResponse = OauthApiUtil.getAccessToken(code, provider);

    OauthUserInfo oauthUserInfo = OauthApiUtil.getOauthUserInfo(providerName, tokenResponse, provider);

    // TODO: 추후에 providerName으로 분기 처리
    Users user = usersRepository.findBykakaoId(oauthUserInfo.getMail())
        .orElseThrow(() -> new UsersException(UserErrorCode.NOT_FOUND_OAUTH_USER, oauthUserInfo.getMail()));
    TokenInfo tokenInfo = jwtTokenUtil.getOauthJwtToken(user);

    user.issueRefreshToken(tokenInfo);
    return new LoginResponse(user, tokenInfo);
  }


}
