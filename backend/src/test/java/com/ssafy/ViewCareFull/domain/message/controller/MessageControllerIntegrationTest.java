package com.ssafy.ViewCareFull.domain.message.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.message.dto.MessageDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageRequestDto;
import com.ssafy.ViewCareFull.domain.message.entity.Message;
import com.ssafy.ViewCareFull.domain.message.repository.MessageRepository;
import com.ssafy.ViewCareFull.domain.message.service.MessageService;
import com.ssafy.ViewCareFull.domain.users.dto.LinkRequestDto;
import com.ssafy.ViewCareFull.domain.users.dto.LoginForm;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@DisplayName("메세지 컨트롤러 Test")
@ActiveProfiles("test")
class MessageControllerIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private MessageService messageService;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UsersService usersService;
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserLinkService userLinkService;
  @Autowired
  private DatabaseCleanup databaseCleanup;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {

    // database truncate and start id 1
    databaseCleanup.execute();

    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
    PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
    Hospital hospital = saveHospital(passwordEncoder);
    saveCaregiver(passwordEncoder, hospital);
    Guardian guardian = saveGuardian(passwordEncoder);
    makeLink(guardian);
    saveMessage();
  }

  private void saveMessage() {
    Message message = Message.builder()
        .fromId("caregiver")
        .toId("guardian")
        .title("test")
        .content("test")
        .build();
    messageRepository.save(message);
  }

  private void makeLink(Guardian guardian) {
    userLinkService.addLink(new SecurityUsers(guardian), LinkRequestDto.builder()
        .targetCode("token")
        .relationship("test")
        .build());
  }

  private Guardian saveGuardian(PasswordEncoder passwordEncoder) {
    Guardian guardian = Guardian.builder()
        .domainId("guardian")
        .password(passwordEncoder.encode("1234"))
        .userName("guardian")
        .build();
    usersRepository.save(guardian);
    return guardian;
  }

  private void saveCaregiver(PasswordEncoder passwordEncoder, Hospital hospital) {
    Caregiver caregiver = Caregiver.builder()
        .domainId("caregiver")
        .password(passwordEncoder.encode("1234"))
        .userName("caregiver")
        .hospital(hospital)
        .token("token")
        .build();
    usersRepository.save(caregiver);
  }

  private Hospital saveHospital(PasswordEncoder passwordEncoder) {
    Hospital hospital = Hospital.builder()
        .domainId("hospital")
        .password(passwordEncoder.encode("1234"))
        .userName("hospital")
        .build();
    usersRepository.save(hospital);
    return hospital;
  }

  @Nested
  @DisplayName("메세지 조회")
  class getMessage {

    @Test
    @DisplayName("[성공] 보호자 메세지 조회")
    void getMessages() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.get("/msg")
              .header("Authorization", getGuardianAccessToken())
              .param("page", "1")
              .param("keyword", ""))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("보호자 메세지 조회"));

    }

    @Test
    @DisplayName("[성공] 간병인 메세지 조회")
    void getMessages2() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.get("/msg")
              .header("Authorization", getCaregiverAccessToken())
              .param("page", "1")
              .param("keyword", ""))
          .andExpect(status().isOk());
    }
  }

  @Nested
  @DisplayName("메세지 전송")
  class sendMessage {

    @Test
    @DisplayName("[성공] 메세지 전송 - 간병인 => 보호자")
    void sendMessage() throws Exception {

      MessageRequestDto messageRequestDto = MessageRequestDto.builder()
          .to("guardian")
          .title("test")
          .content("test")
          .build();

      mockMvc.perform(RestDocumentationRequestBuilders.post("/msg")
              .header("Authorization", getCaregiverAccessToken())
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(messageRequestDto)))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentationWrapper.document("메세지 전송"));

      MessageDto messageDto = messageService.readMessage(new SecurityUsers(usersService.getUser("guardian")), "1");
      Assertions.assertThat(messageDto.getContent()).isEqualTo("test");
    }
  }

  @Nested
  @DisplayName("메세지 단건 조회")
  class getMessageDetail {

    @Test
    @DisplayName("[성공] 메세지 단건 조회")
    void getMessageDetail() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.get("/msg/{id}", 1)
              .header("Authorization", getGuardianAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("메세지 단건 조회"));
    }

  }

  @Nested
  @DisplayName("메세지 읽기")
  class readMessage {

    @Test
    @DisplayName("[성공] 메세지 읽기")
    void readMessage() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.post("/msg/{id}", 1)
              .header("Authorization", getGuardianAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("메세지 읽기"));
      MessageDto messageDto = messageService.readMessage(new SecurityUsers(usersService.getUser("guardian")), "1");
      Assertions.assertThat(messageDto.getIsRead()).isTrue();
    }
  }

  @Nested
  @DisplayName("메세지 삭제")
  class deleteMessage {

    @Test
    @DisplayName("[성공] 메세지 삭제")
    void deleteMessage() throws Exception {
      mockMvc.perform(RestDocumentationRequestBuilders.delete("/msg/{id}", 1)
              .header("Authorization", getGuardianAccessToken()))
          .andExpect(status().isNoContent())
          .andDo(MockMvcRestDocumentationWrapper.document("메세지 삭제"));

      Assertions.assertThat(messageRepository.findById(1L)).isEmpty();
    }
  }

  private String getCaregiverAccessToken() {
    return "Bearer " + usersService.login(LoginForm.builder()
        .id("caregiver")
        .password("1234")
        .build()).getAccessToken();
  }

  private String getGuardianAccessToken() {
    return "Bearer " + usersService.login(LoginForm.builder()
        .id("guardian")
        .password("1234")
        .build()).getAccessToken();
  }
}