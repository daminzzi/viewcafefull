package com.ssafy.ViewCareFull.domain.users.security;

import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.security.jwt.ViewCareFullUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ViewCareFullAuthenticationProvider extends DaoAuthenticationProvider {

  private final ViewCareFullUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public ViewCareFullAuthenticationProvider(ViewCareFullUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    super.setUserDetailsService(userDetailsService);
    super.setPasswordEncoder(passwordEncoder);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws UsersException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    UserDetails user = userDetailsService.loadUserByUsername(username);

    if (user == null || !this.passwordEncoder.matches(password, user.getPassword())) {
      throw new UsersException(UserErrorCode.NOT_MATCHING_LOGIN_INFO);
    }

    return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
