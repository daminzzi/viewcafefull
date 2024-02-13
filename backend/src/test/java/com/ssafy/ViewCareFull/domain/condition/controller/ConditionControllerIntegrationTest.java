package com.ssafy.ViewCareFull.domain.condition.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionResponseDto;
import com.ssafy.ViewCareFull.domain.condition.entity.ConditionType;
import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.condition.repository.ConditionRepository;
import com.ssafy.ViewCareFull.domain.condition.service.ConditionService;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
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
  @Autowired
  private ConditionService conditionService;

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
      ConditionRequestDto conditionRequestDto = new ConditionRequestDto("20240208", "good");
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/condition")
              .content(objectMapper.writeValueAsString(conditionRequestDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentation.document("컨디션 생성"));
      // then
      Conditions conditions = conditionRepository.findByUserAndDate(caregiver, LocalDate.of(2024, 2, 8))
          .get();
      Assertions.assertThat(conditions.getUser().getId()).isEqualTo(caregiver.getId());
      Assertions.assertThat(conditions.getCondition()).isEqualTo(ConditionType.GOOD);
    }

    @Test
    @DisplayName("[성공] 입소자가 컨디션 수정 테스트")
    void modifyConditionByCaregiver() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      Conditions savedCondition = Conditions.builder()
          .user(caregiver)
          .date(LocalDate.of(2024, 2, 8))
          .condition(ConditionType.GOOD)
          .build();
      conditionRepository.save(savedCondition);
      ConditionRequestDto conditionRequestDto = new ConditionRequestDto("20240208", "normal");
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/condition")
              .content(objectMapper.writeValueAsString(conditionRequestDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("컨디션 수정"));
      // then
      Conditions conditions = conditionRepository.findByUserAndDate(caregiver, LocalDate.of(2024, 2, 8))
          .get();
      Assertions.assertThat(conditions.getUser().getId()).isEqualTo(caregiver.getId());
      Assertions.assertThat(conditions.getCondition()).isEqualTo(ConditionType.NORMAL);
    }
  }

  @Nested
  @DisplayName("컨디션 조회 테스트")
  class GetConditionTest {

    LocalDate date = LocalDate.of(2024, 1, 1);

    List<Conditions> createConditions(Users caregiver, long days) {
      List<Conditions> createdConditions = new ArrayList<>();
      for (long i = 0L; i < days; i++) {
        Conditions savedCondition = Conditions.builder()
            .user(caregiver)
            .date(date.plusDays(i))
            .condition(ConditionType.values()[(int) Math.random() * 3])
            .build();
        createdConditions.add(savedCondition);
      }
      return createdConditions;
    }

    @Test
    @DisplayName("[성공] 입력되지 않은 컨디션이 있는 기간별 리스트 조회 테스트")
    void isNullInConditionListDuringDateTest() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      List<Conditions> createdConditionList = createConditions(caregiver, 29L); //29일까지 랜덤 저장
      createdConditionList.add(Conditions.builder() // 30일 저장
          .user(caregiver)
          .date(date.plusDays(29L))
          .condition(ConditionType.GOOD)
          .build());
      conditionRepository.saveAll(createdConditionList);

      // when
      mockMvc.perform(RestDocumentationRequestBuilders.get("/condition")
              .param("start", "2024-01-01")
              .param("end", "2024-01-31")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("기간별 컨디션 조회"));
      //then
      List<Conditions> conditionListByRepository = conditionRepository.findByUserAndDateBetween(caregiver, date,
          date.plusDays(30));
      List<ConditionResponseDto> conditionListByService = conditionService.getCondition(new SecurityUsers(caregiver),
          date, date.plusDays(30));

      Assertions.assertThat(conditionListByRepository).hasSize(30);
      Assertions.assertThat(conditionListByService).hasSize(31);
      Assertions.assertThat(conditionListByService.get(30).getDate()).isEqualTo("2024-01-31");
      for (int i = 0; i < 30; i++) {
        Assertions.assertThat(conditionListByService.get(i).getDate()).isNotNull();
      }
      Assertions.assertThat(conditionListByService.get(29).getData()).isEqualTo("좋음");
      Assertions.assertThat(conditionListByService.get(30).getData()).isNull();
    }

    @Test
    @DisplayName("[성공] 기간별 컨디션 조회 테스트")
    void getConditionListDuringDateTest() throws Exception {
      // given
      Users caregiver = userRegisterHelper.getCaregiver();
      conditionRepository.saveAll(createConditions(caregiver, 31L));
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.get("/condition")
              .param("start", "2024-01-01")
              .param("end", "2024-01-31")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("기간별 컨디션 조회"));
      // then
      List<Conditions> conditionListByRepository = conditionRepository.findByUserAndDateBetween(caregiver, date,
          date.plusDays(30));
      List<ConditionResponseDto> conditionListByService = conditionService.getCondition(new SecurityUsers(caregiver),
          date, date.plusDays(30));

      Assertions.assertThat(conditionListByRepository).hasSize(31);
      Assertions.assertThat(conditionListByService).hasSize(31);
      Assertions.assertThat(conditionListByService.get(30).getDate()).isEqualTo("2024-01-31");
      conditionListByService.stream()
          .map(ConditionResponseDto::getDate)
          .forEach(date -> Assertions.assertThat(date).isNotNull());
    }
  }
}
