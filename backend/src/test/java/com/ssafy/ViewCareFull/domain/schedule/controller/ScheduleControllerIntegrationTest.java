package com.ssafy.ViewCareFull.domain.schedule.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.schedule.entity.DayType;
import com.ssafy.ViewCareFull.domain.schedule.entity.Schedule;
import com.ssafy.ViewCareFull.domain.schedule.repository.ScheduleRepository;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
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
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@DisplayName("일정 컨트롤러 Test")
@ActiveProfiles("test")
public class ScheduleControllerIntegrationTest {

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
  private ScheduleRepository scheduleRepository;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

  void createSchedule() {
    for (int i = 0; i < 7; i++) {
      Schedule schedule = Schedule.builder()
          .hospital(userRegisterHelper.getHospital())
          .day(DayType.getNumberDayType(i % 6))
          .startTime(LocalTime.of(10, 30))
          .endTime(LocalTime.of(20, 0))
          .unit(30)
          .build();
      scheduleRepository.save(schedule);
    }
  }

  @Nested
  @DisplayName("일정 조회 테스트")
  class SaveConditionTest {

    @Test
    @DisplayName("[성공] 요양원의 일정 조회 테스트")
    void readHospitalScheduleTest() throws Exception {
      // given
      createSchedule();
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.get("/schedule/{domain-id}", "hospital")
              .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("일정 조회"));
      // then
      List<Schedule> scheduleList = scheduleRepository.findAllById(userRegisterHelper.getHospital().getId());
      Assertions.assertThat(scheduleList).hasSize(7);
    }
  }


}
