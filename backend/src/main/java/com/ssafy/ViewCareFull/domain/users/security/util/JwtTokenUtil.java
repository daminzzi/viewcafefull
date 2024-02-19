package com.ssafy.ViewCareFull.domain.users.security.util;

import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  public static String getAccessToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public TokenInfo getJwtToken(String id, String password) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(id, password);
    System.out.println("=====================================");
    System.out.println("id: " + id + " password: " + password);
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    return jwtTokenProvider.generateToken((SecurityUsers) authentication.getPrincipal());
  }

  public TokenInfo getOauthJwtToken(Users user) {
    return jwtTokenProvider.generateToken((SecurityUsers
        .builder()
        .user(user)
        .build()));
  }

//  public String reissueAccessToken(String refreshToken) {
//    if (!jwtTokenProvider.validateToken(refreshToken)) {
//      throw new UsersException(UserErrorCode.INVALID_REFRESH_TOKEN);
//    }
//
//    Authentication authentication = jwtTokenProvider.getAuthentication(request.getAccessToken());
//
//    if (!StringUtils.hasText(refreshToken)) {
//      throw new UsersException(UserErrorCode.INVALID_REFRESH_TOKEN);
//    }
//    if (!refreshToken.equals(request.getRefreshToken())) {
//      throw new BadRequestException("refresh token", request.getRefreshToken());
//    }
//
//    return jwtTokenProvider. (refreshToken);
//  }
}
