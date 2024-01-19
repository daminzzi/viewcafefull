package com.ssafy.ViewCareFull.domain.users.security;

import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JwtTokenProvider {

  private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

  private final Key key;

  private final UserDetailsService userDetailsService;

  @Autowired
  public JwtTokenProvider(@Value("${jwt.secret_key}") String secretKey, UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenInfo generateToken(Authentication authentication) {
    SecurityUsers securityUser = (SecurityUsers) authentication.getPrincipal();
    String auth = securityUser.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
    String id = securityUser.getUsername();
    long now = (new Date()).getTime();

    Date accessExpiredTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setSubject(authentication.getName())
        .claim("auth", auth)
        .claim("id", id)
        .setExpiration(accessExpiredTime)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
    log.info("accessToken = {}", accessToken);

    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
    log.info("refreshToken = {}", refreshToken);

    return TokenInfo.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public Authentication decodeToken(String accessToken) {
    Claims claims = parseClaims(accessToken);
    if (claims.get("auth") == null) {
      throw new UsersException(UserErrorCode.UNAUTHIRUZE_USER);
    }

    String username = claims.getSubject();
    SecurityUsers securityUser = (SecurityUsers) userDetailsService.loadUserByUsername(username);
    Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
    return new UsernamePasswordAuthenticationToken(username, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);
    }
    return false;
  }

  private Claims parseClaims(String accessToken) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(accessToken)
        .getBody();
  }

}
