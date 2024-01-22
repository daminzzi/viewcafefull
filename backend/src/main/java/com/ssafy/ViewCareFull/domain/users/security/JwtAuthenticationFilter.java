package com.ssafy.ViewCareFull.domain.users.security;


import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final UsersRepository usersRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = SecurityUtil.getAccessToken(request);

    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
      // 토큰이 유효할 경우 토큰에서 authentication 객체(사용자 정보)를 받아온다.
      Authentication authentication = jwtTokenProvider.decodeToken(token);

      // authentication 객체에 사용자 정보를 추가한다.
      Users user = usersRepository.findByDomainId(authentication.getName())
          .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
      SecurityUsers securityUser = new SecurityUsers(user);

      Authentication customAuthentication = new UsernamePasswordAuthenticationToken(
          securityUser, null, securityUser.getAuthorities());

      // SecurityContext에 authentication 객체를 저장한다.
      SecurityContextHolder.getContext().setAuthentication(customAuthentication);
    }

    filterChain.doFilter(request, response);
  }
}
