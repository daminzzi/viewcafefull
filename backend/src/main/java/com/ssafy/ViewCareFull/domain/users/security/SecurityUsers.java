package com.ssafy.ViewCareFull.domain.users.security;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUsers implements UserDetails {

  private String userId;
  private String password;
  private String userName;
  private String userType;

  public SecurityUsers(Users user) {
    this.userId = user.getDomainId();
    this.password = user.getPassword();
    this.userName = user.getUserName();
    this.userType = user.getUserType();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(userType));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
