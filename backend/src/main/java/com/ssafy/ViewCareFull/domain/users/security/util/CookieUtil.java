package com.ssafy.ViewCareFull.domain.users.security.util;

import com.ssafy.ViewCareFull.domain.users.dto.LoginResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

  public static ResponseCookie convertRefreshTokenToCookie(LoginResponse loginResponse) {
    return ResponseCookie.from("refresh-token", loginResponse.getRefreshToken())
        .path("/")
        .sameSite("None")
        .httpOnly(true)
        .secure(true)
        .maxAge(60 * 60 * 24)
        .build();
  }

  public static void deleteRefreshTokenCookie() {
    ResponseCookie.from("refresh-token", " ")
        .path("/")
        .sameSite("None")
        .httpOnly(true)
        .secure(true)
        .maxAge(0)
        .build();
  }
}
