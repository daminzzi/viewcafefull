package com.ssafy.ViewCareFull.domain.condition.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.common.entity.TimeType;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.entity.ConditionType;
import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.condition.repository.ConditionRepository;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@DisplayName("컨디션 컨트롤러 Test")
@ActiveProfiles("test")
public class ConditionControllerIntegrationTest {

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
  private ConditionRepository conditionRepository;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    objectMapper.registerModule(new JavaTimeModule());
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

  @Nested
  @DisplayName("컨디션 저장 테스트")
  class SaveConditionTest {

    @Test
    @DisplayName("[성공] 입소자가 컨디션 생성 테스트")
    void saveConditionByCaregiverTest() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      ConditionRequestDto conditionRequestDto = ConditionRequestDto.builder()
          .day(LocalDate.now())
          .time("morning")
          .condition("good").build();
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/condition")
              .content(objectMapper.writeValueAsString(conditionRequestDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentation.document("컨디션 생성"));
      // then
      Conditions conditions = conditionRepository.findByUserAndDateAndTime(caregiver, LocalDate.now(), TimeType.MORNING)
          .get();
      Assertions.assertThat(conditions.getUser().getId()).isEqualTo(1L);
      Assertions.assertThat(conditions.getCondition()).isEqualTo(ConditionType.GOOD);
      Assertions.assertThat(conditions.getTime()).isEqualTo(TimeType.MORNING);
    }

    @Test
    @DisplayName("[성공] 입소자가 컨디션 수정 테스트")
    void modifyConditionByCaregiver() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      Conditions savedCondition = Conditions.builder()
          .user(caregiver)
          .date(LocalDate.now())
          .time(TimeType.MORNING)
          .condition(ConditionType.GOOD)
          .build();
      conditionRepository.save(savedCondition);
      ConditionRequestDto conditionRequestDto = ConditionRequestDto.builder()
          .day(LocalDate.now())
          .time("morning")
          .condition("normal").build();
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/condition")
              .content(objectMapper.writeValueAsString(conditionRequestDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("컨디션 수정"));
      // then
      Conditions conditions = conditionRepository.findByUserAndDateAndTime(caregiver, LocalDate.now(), TimeType.MORNING)
          .get();
      Assertions.assertThat(conditions.getUser().getId()).isEqualTo(1L);
      Assertions.assertThat(conditions.getCondition()).isEqualTo(ConditionType.NORMAL);
      Assertions.assertThat(conditions.getTime()).isEqualTo(TimeType.MORNING);
    }
  }

  @Nested
  @DisplayName("컨디션 조회 테스트")
  class GetConditionTest {

    @Test
    @DisplayName("[성공] 기간별 컨디션 조회 테스트")
    void getConditionTest() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      LocalDate date = LocalDate.of(2024, 1, 1);
      int listSize = 0;
      List<Conditions> addConditions = new ArrayList<>();
      for (long i = 0L; i < 31L; i++) {
        for (int j = 0; j < 3; j++) {
          Conditions savedCondition = Conditions.builder()
              .user(caregiver)
              .date(date.plusDays(i))
              .time(TimeType.values()[j])
              .condition(ConditionType.values()[j])
              .build();
          addConditions.add(savedCondition);
          listSize++;
        }
        conditionRepository.saveAll(addConditions);
      }
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.get("/condition")
              .param("start", "2024-01-01")
              .param("end", "2024-01-31")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("컨디션 조회"));
      //then
      Assertions.assertThat(conditionRepository.findByUserAndDateBetween(caregiver, date, date.plusDays(30)).size())
          .isEqualTo(listSize);
    }
  }
}
