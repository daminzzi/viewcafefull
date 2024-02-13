package com.ssafy.ViewCareFull.domain.users.controller;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.dto.LoginForm;
import com.ssafy.ViewCareFull.domain.users.dto.LoginResponse;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.security.util.CookieUtil;
import com.ssafy.ViewCareFull.domain.users.service.OAuthUserService;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

  private final UsersService usersService;
  private final OAuthUserService oAuthUserService;

  @PostMapping
  public ResponseEntity<Void> signup(@RequestBody JoinForm joinForm) {
    usersService.signup(joinForm);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/validation/{id}")
  public ResponseEntity<Void> validation(@PathVariable(name = "id") String id) {
    usersService.duplicatedId(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/signin")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {
    LoginResponse loginResponse = usersService.login(loginForm);
    ResponseCookie refreshTokenCookie = CookieUtil.convertRefreshTokenToCookie(loginResponse);
    loginResponse.removeRefreshToken();
    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .body(loginResponse);
  }

  @GetMapping("/{provider}/signin")
  public ResponseEntity<LoginResponse> oauthLogin(@PathVariable String provider,
      @RequestParam String code) {
    LoginResponse loginResponse = oAuthUserService.login(provider, code);
    ResponseCookie refreshTokenCookie = CookieUtil.convertRefreshTokenToCookie(loginResponse);
    loginResponse.removeRefreshToken();
    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .body(loginResponse);
  }

  @GetMapping("/signout")
  public ResponseEntity<Void> signout(@AuthenticationPrincipal SecurityUsers securityUsers,
      @PathParam("id") String id) {
    usersService.deleteRefreshToken(securityUsers, id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .header(HttpHeaders.SET_COOKIE, CookieUtil.deleteRefreshTokenCookie().toString()).build();
  }

  @PostMapping("/token")
  public ResponseEntity<String> reissue(HttpServletRequest request) {
    String refreshToken = request.getHeader("refresh-token");
    return ResponseEntity.status(HttpStatus.OK).build();
//        .body(usersService.reissue(refreshToken));
  }
}
