package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
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

  @Transactional
  public void singup(JoinForm joinForm) {
    if (usersRepository.existsByDomainId(joinForm.getId())) {
      throw new UsersException(UserErrorCode.DUPLICATED_ID);
    }
    usersRepository.save(Guardian.createUser(joinForm, passwordEncoder));
  }

  public boolean duplicatedId(String id) {
    return usersRepository.existsByDomainId(id);
  }
}
