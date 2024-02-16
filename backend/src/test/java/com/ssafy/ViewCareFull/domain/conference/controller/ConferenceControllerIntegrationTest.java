package com.ssafy.ViewCareFull.domain.conference.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto.Participant;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.repository.ConferenceRepository;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@DisplayName("면회 컨트롤러 Test")
@ActiveProfiles("test")
class ConferenceControllerIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private DatabaseCleanup databaseCleanup;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserRegisterHelper userRegisterHelper;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private ConferenceRepository conferenceRepository;
  @Autowired
  private ConferenceService conferenceService;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

  public Conference saveConference() {
    LocalDateTime conferenceDateTime = LocalDateTime.now();
    Conference conference = Conference.createConference(
        conferenceDateTime.toLocalDate()
        , conferenceDateTime.toLocalTime());

    conference.linkApplicationUsers(userRegisterHelper.getUserLink());
    conferenceRepository.save(conference);
    return conference;
  }

  @Nested
  @DisplayName("면회 생성 테스트")
  class createConferenceTest {

    @Test
    @DisplayName("[성공] 면회 생성 성공")
    void createConferenceSuccessTest() throws Exception {
      // given
      LocalDateTime conferenceDateTime = LocalDateTime.now();
      ConferenceReservationDto conferenceReservationDto = ConferenceReservationDto.builder()
          .conferenceDate(conferenceDateTime.toLocalDate())
          .conferenceTime(
              LocalTime.of(conferenceDateTime.toLocalTime().getHour(), conferenceDateTime.toLocalTime().getMinute()))
          .applicationList(List.of(new Participant(userRegisterHelper.getGuardian().getDomainId())))
          .targetId(userRegisterHelper.getCaregiver().getDomainId())
          .permissionId(userRegisterHelper.getHospital().getDomainId())
          .build();
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/conference")
              .content(objectMapper.writeValueAsString(conferenceReservationDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentation.document("면회 생성"));
      // then
      Assertions.assertThat(conferenceRepository.existsById(1L)).isTrue();

    }
  }

  @Nested
  @DisplayName("면회 승인 변경 테스트")
  class modifyConferenceStateTest {

    @Test
    @DisplayName("[성공] 면회 승인 시 방생성 성공")
    void modifyConferenceStateSuccessTest() throws Exception {
      // given
      saveConference();

      ConferenceStateDto conferenceStateDto = new ConferenceStateDto("A");
      // when
      mockMvc.perform(
              RestDocumentationRequestBuilders.patch("/conference/{id}", 1L)
                  .content(objectMapper.writeValueAsString(conferenceStateDto))
                  .header("Content-Type", "application/json")
                  .header("Authorization", userRegisterHelper.getHospitalAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("면회 승인"));
      // then
      Conference savedConference = conferenceRepository.findById(1L).get();
      Assertions.assertThat(savedConference.getConferenceState()).isEqualTo(PermissionType.A);
    }
  }

  @Nested
  @DisplayName("면회 조회 테스트")
  class getConferenceListTest {

    @Test
    @DisplayName("[성공] 요양원이 면회 조회 성공 테스트")
    void getConferenceListByHospitalSuccessTest() throws Exception {
      // given
      Conference conference = saveConference();
      LocalDate today = LocalDate.now();
      // when
      mockMvc.perform(
              RestDocumentationRequestBuilders.get("/conference/per/list")
                  .header("Content-Type", "application/json")
                  .header("Authorization", userRegisterHelper.getHospitalAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("면회 조회"));
    }

    @Test
    @DisplayName("[성공] 보호자가 면회 조회 성공 테스트")
    void getConferenceListByGuardianSuccessTest() throws Exception {
      // given
      Conference conference = saveConference();
      LocalDate today = LocalDate.now();
      // when
      mockMvc.perform(
              RestDocumentationRequestBuilders.get("/conference/app/list")
                  .header("Content-Type", "application/json")
                  .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("면회 조회"));
    }
  }

  @Test
  @DisplayName("[성공] 입소자가 면회 조회 성공 테스트")
  void getConferenceListByGuardianSuccessTest() throws Exception {
    // given
    Conference conference = saveConference();
    LocalDate today = LocalDate.now();
    // when
    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/conference/tar/list")
                .header("Content-Type", "application/json")
                .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentation.document("면회 조회"));
  }
}
