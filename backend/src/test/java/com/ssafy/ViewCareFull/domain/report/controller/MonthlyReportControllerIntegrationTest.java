package com.ssafy.ViewCareFull.domain.report.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.report.entity.Reports;
import com.ssafy.ViewCareFull.domain.report.repository.ReportRepository;
import com.ssafy.ViewCareFull.domain.report.service.MonthlyMovieService;
import com.ssafy.ViewCareFull.domain.report.service.MonthlyReportService;
import com.ssafy.ViewCareFull.domain.report.util.OpenAIApi;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@DisplayName("월간 리포트 컨트롤러 Test")
@ActiveProfiles("test")
public class MonthlyReportControllerIntegrationTest {

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
  private ReportRepository reportRepository;
  @MockBean
  private OpenAIApi OpenAIApi;
  @MockBean
  private MonthlyMovieService monthlyMovieService;
  @InjectMocks
  private MonthlyReportService monthlyReportService;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

  @Nested
  @DisplayName("월간 리포트 테스트")
  class ReportTest {

//    @Test
//    @DisplayName("[성공] 월간 리포트 생성 테스트")
//    void createMonthlyReportTest() throws Exception {
//      // given
//      RequestReportDto requestReportDto = new RequestReportDto(202402, userRegisterHelper.getCaregiver().getId());
//      Mockito.when(OpenAIApi.getMonthlyReportResponse(Mockito.any(MonthlyHealthInfo.class)))
//          .thenReturn(new MonthlyReport());
//      Mockito.when(monthlyMovieService.createMonthlyMovie(Mockito.any(), Mockito.anyInt()))
//          .thenReturn("/video/1.mp4");
//      // when
//      mockMvc.perform(RestDocumentationRequestBuilders.post("/report")
//              .content(objectMapper.writeValueAsString(requestReportDto))
//              .header("Content-Type", "application/json")
//              .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
//          .andExpect(status().isOk())
//          .andDo(MockMvcRestDocumentation.document("월간리포트 생성"));
//      //then
//      Assertions.assertThat(reportRepository.existsById(1L)).isTrue();
//    }
  }

  @Test
  @DisplayName("[성공] 월간 리포트 조회 테스트")
  void getMonthlyReportSuccessTest() throws Exception {
    // given
    Long caregiverId = userRegisterHelper.getCaregiver().getId();
    Reports report = Reports.builder()
        .year(2024)
        .month(2)
        .caregiverId(caregiverId)
        .reportInfo("{}")
        .build();
    reportRepository.save(report);

    // when
    mockMvc.perform(RestDocumentationRequestBuilders.get("/report/{id}", caregiverId)
            .param("month", "202402")
            .header("Content-Type", "application/json")
            .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentation.document("월간리포트 조회"));
    //then
    Reports findMonthlyReport = reportRepository.findByIdAndDate(caregiverId, 2024, 2).get();
    Assertions.assertThat(findMonthlyReport.getId()).isEqualTo(1L);
    Assertions.assertThat(findMonthlyReport.getYear()).isEqualTo(2024);
    Assertions.assertThat(findMonthlyReport.getMonth()).isEqualTo(2);
  }

  @Test
  @DisplayName("[실패] 월간 리포트 조회 테스트")
  void getMonthlyReportFailureTest() throws Exception {
    // given
    Long caregiverId = userRegisterHelper.getCaregiver().getId();

    // when
    mockMvc.perform(RestDocumentationRequestBuilders.get("/report/{id}", caregiverId)
            .param("month", "202402")
            .header("Content-Type", "application/json")
            .header("Authorization", userRegisterHelper.getGuardianAccessToken()))
        .andExpect(status().isNotFound())
        .andDo(MockMvcRestDocumentation.document("월간리포트 조회"));
  }
}
