package com.ssafy.ViewCareFull.domain.users.security.jwt;

import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
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

  private static final long ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L; // 하루
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 한달

  private Key key;

  private final UserDetailsService userDetailsService;

  @Autowired
  public JwtTokenProvider(@Value("${jwt.secret_key}") String secretKey, UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenInfo generateToken(SecurityUsers securityUser) {
    String auth = securityUser.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
    String domainId = securityUser.getUsername();
    long now = (new Date()).getTime();

    String accessToken = createAccessToken(securityUser, now, auth, domainId);

    String refreshToken = createRefreshToken(now, domainId);

    return TokenInfo.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private String createRefreshToken(long now, String id) {
    String refreshToken = Jwts.builder()
        .setSubject(id)
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
    log.info("refreshToken = {}", refreshToken);
    return refreshToken;
  }

  public String createAccessToken(SecurityUsers securityUser, long now, String auth, String id) {
    Date accessExpiredTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setSubject(securityUser.getUsername())
        .claim("auth", auth)
        .claim("id", id)
        .setExpiration(accessExpiredTime)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
    log.info("accessToken = {}", accessToken);
    return accessToken;
  }

  public Authentication decodeToken(String accessToken) {
    Claims claims = parseClaims(accessToken);
    if (claims.get("auth") == null) {
      throw new UsersException(UserErrorCode.UNAUTHORIZED_USER);
    }

    String domainId = claims.getSubject();
    SecurityUsers securityUser = (SecurityUsers) userDetailsService.loadUserByUsername(domainId);
    Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
    return new UsernamePasswordAuthenticationToken(domainId, "", authorities);
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
