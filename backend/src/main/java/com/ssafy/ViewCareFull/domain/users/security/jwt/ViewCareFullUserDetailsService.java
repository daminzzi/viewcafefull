package com.ssafy.ViewCareFull.domain.users.security.jwt;

import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewCareFullUserDetailsService implements UserDetailsService {

  private final UsersRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users findUser = repository.findByDomainId(username)
        .orElseThrow(() -> new UsernameNotFoundException("해당 아이디로 가입된 유저가 없습니다."));
    return SecurityUsers.builder()
        .user(findUser)
        .build();
  }
}
