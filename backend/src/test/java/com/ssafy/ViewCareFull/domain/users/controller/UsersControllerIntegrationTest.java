package com.ssafy.ViewCareFull.domain.users.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.dto.LoginForm;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@DisplayName("사용자 컨트롤러 Test")
@ActiveProfiles("test")
public class UsersControllerIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private DatabaseCleanup databaseCleanup;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserRegisterHelper userRegisterHelper;
  @Autowired
  private UsersService usersService;
  @Autowired
  private UsersRepository usersRepository;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .build();
  }

  @Nested
  @DisplayName("중복 아이디 테스트")
  class chkDuplicatedId {

    @Test
    @DisplayName("[성공]중복 아이디가 없을 경우")
    void chkDuplicatedIdSuccess() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.get("/users/validation/{id}", 1)
              .param("id", "user1"))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("중복 아이디 확인"));
      Assertions.assertThat(usersRepository.existsByDomainId("user1")).isFalse();
    }

    @Test
    @DisplayName("[실패]중복 아이디가 있을 경우")
    void chkDuplicatedIdFail() throws Exception {

      Assertions.assertThat(usersRepository.existsByDomainId("guardian")).isTrue();
      mockMvc.perform(RestDocumentationRequestBuilders.get("/users/validation/{id}", "guardian"))
          .andExpect(status().isConflict())
          .andDo(MockMvcRestDocumentationWrapper.document("중복 아이디 확인"));
    }

  }

  @Nested
  @DisplayName("회원가입 테스트")
  class join {

    @Test
    @DisplayName("[성공]보호자 회원가입")
    void joinGuardianSuccess() throws Exception {
      JoinForm joinForm = new JoinForm("user2", "1234", "유저2", "010-1234-1234", "2024-02-06", "");
      mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(joinForm)))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentationWrapper.document("보호자 서비스단 회원가입"));
      Assertions.assertThat(usersRepository.findByDomainId("user2")).isNotNull();
    }
  }

  @Nested
  @DisplayName("로그인 테스트")
  class login {

    @Test
    @DisplayName("[성공]로그인 성공")
    void loginSuccess() throws Exception {
      LoginForm loginForm = new LoginForm("guardian", "1234");
      mockMvc.perform(RestDocumentationRequestBuilders.post("/users/signin")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(loginForm)))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("로그인"));
    }

    @Test
    @DisplayName("[실패] 회원가입된 적 없는 아이디로 로그인 시도")
    void wrongId() throws Exception {
      LoginForm loginForm = new LoginForm("user1", "1234");
      mockMvc.perform(RestDocumentationRequestBuilders.post("/users/signin")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(loginForm)))
          .andExpect(status().isNotFound())
          .andDo(MockMvcRestDocumentationWrapper.document("로그인"));
    }

    @Test
    @DisplayName("[실패] 다른 비밀번호로 로그인 시도")
    void wrongPassword() throws Exception {
      LoginForm loginForm = new LoginForm("guardian", "1111");
      mockMvc.perform(RestDocumentationRequestBuilders.post("/users/signin")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(loginForm)))
          .andExpect(status().isUnauthorized())
          .andDo(MockMvcRestDocumentationWrapper.document("로그인"));
    }
  }
}
