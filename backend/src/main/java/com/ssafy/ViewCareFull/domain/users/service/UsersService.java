package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.dto.LoginForm;
import com.ssafy.ViewCareFull.domain.users.dto.LoginResponse;
import com.ssafy.ViewCareFull.domain.users.dto.TokenInfo;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;

  @Transactional
  public void singup(JoinForm joinForm) {
    if (usersRepository.existsByDomainId(joinForm.getId())) {
      throw new UsersException(UserErrorCode.DUPLICATED_ID);
    }
    usersRepository.save(Guardian.createUser(joinForm, passwordEncoder));
  }

  public void duplicatedId(String id) {
    if (usersRepository.existsByDomainId(id)) {
      throw new UsersException(UserErrorCode.DUPLICATED_ID);
    }
  }

  @Transactional
  public LoginResponse login(LoginForm loginForm) {
    Users user = usersRepository.findByDomainId(loginForm.getId())
        .orElseThrow(() -> new UsersException(UserErrorCode.NOT_FOUND_USERID));

    TokenInfo tokenInfo = jwtTokenUtil.getJwtToken(loginForm.getId(), loginForm.getPassword());

    user.issueRefreshToken(tokenInfo);
    return new LoginResponse(user, tokenInfo);
  }
}
