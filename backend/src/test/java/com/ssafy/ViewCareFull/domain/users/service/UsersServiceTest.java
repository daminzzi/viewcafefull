package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

  @InjectMocks
  UsersService usersService;
  @Mock
  UsersRepository usersRepository;
  @Spy
  PasswordEncoder passwordEncoder;

  @ParameterizedTest
  @ValueSource(strings = {"user1", "user2", "user3"})
  @DisplayName("중복 아이디일 경우")
  void duplicatedIdSuccessTest(String existedUserId) {
    Mockito.when(usersRepository.existsByDomainId(existedUserId)).thenReturn(true);

    Assertions.assertThrows(UsersException.class,
        () -> usersService.duplicatedId(existedUserId));
  }

  @ParameterizedTest
  @ValueSource(strings = {"user1", "user2", "user3"})
  @DisplayName("중복 아이디가 아닐 경우")
  void duplicatedIdFailTest(String unexistedUserId) {
    Mockito.when(usersRepository.existsByDomainId(unexistedUserId)).thenReturn(false);

    Assertions.assertDoesNotThrow(() -> usersService.duplicatedId(unexistedUserId));
  }

  @ParameterizedTest
  @ValueSource(strings = {"user1", "user2", "user3"})
  @DisplayName("서비스단에서 회원가입 성공")
  void signUpOnServiceSuccessTest(String unexistedUserId) {
    Mockito.when(usersRepository.existsByDomainId(unexistedUserId)).thenReturn(false);
    usersService.signup(JoinForm.builder()
        .id(unexistedUserId)
        .password("password")
        .build());
    Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any());
  }

  @ParameterizedTest
  @ValueSource(strings = {"user1", "user2", "user3"})
  @DisplayName("서비스단에서 중복 아이디로 인하여 회원가입 실패")
  void signUpOnServiceFailTest(String existedUserId) {
    Mockito.when(usersRepository.existsByDomainId(existedUserId)).thenReturn(true);
    Assertions.assertAll(
        () -> Assertions.assertThrows(UsersException.class, () ->
            usersService.signup(JoinForm.builder()
                .id(existedUserId)
                .password("password")
                .build())
        ),
        () -> Mockito.verify(usersRepository, Mockito.times(0)).save(Mockito.any())
    );
  }

}