package com.ssafy.ViewCareFull.domain.health.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.entity.HealthType;
import com.ssafy.ViewCareFull.domain.health.repository.HealthRepository;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@DisplayName("헬스 컨트롤러 Test")
@ActiveProfiles("test")
class HealthControllerTest {

  private MockMvc mockMvc;
  @Autowired
  private DatabaseCleanup databaseCleanup;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private UserRegisterHelper userRegisterHelper;
  @Autowired
  private HealthRepository healthRepository;

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
  @DisplayName("건강 정보 등록")
  class CreateHealthInfoTest {

    @Test
    @DisplayName("[성공] 건강 정보 등록 테스트")
    void createHealthInfo() throws Exception {
      // given
      HealthInfo healthInfo = HealthInfo.builder()
          .healthType("L")
          .level(1)
          .healthDate("2021-10-10")
          .build();
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.post("/health/{domain-id}", "caregiver")
              .content(new ObjectMapper().writeValueAsString(healthInfo))
              .contentType(MediaType.APPLICATION_JSON)
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isCreated())
          .andDo(document("health-create",
              RequestDocumentation.pathParameters(
                  RequestDocumentation.parameterWithName("domain-id").description("도메인 아이디")
              )
          ));
      // then
      Health referenceById = healthRepository.getReferenceById(1L);
      Assertions.assertThat(referenceById.getHealthType()).isEqualTo(HealthType.L);
      Assertions.assertThat(referenceById.getLevel()).isEqualTo(1);
    }
  }

  @Nested
  @DisplayName("건강 정보 삭제")
  class DeleteHealthInfoTest {

    @Test
    @DisplayName("[성공] 건강 정보 삭제 테스트")
    void deleteHealthInfo() throws Exception {
      // given
      HealthInfo healthInfo = HealthInfo.builder()
          .healthType("L")
          .level(1)
          .healthDate("2021-10-10")
          .build();
      healthRepository.save(new Health(userRegisterHelper.getCaregiver(), healthInfo));
      // when
      mockMvc.perform(RestDocumentationRequestBuilders.delete("/health/{id}", 1)
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isNoContent())
          .andDo(document("health-delete",
              RequestDocumentation.pathParameters(
                  RequestDocumentation.parameterWithName("id").description("삭제할 건강 정보 아이디")
              )
          ));
      // then
      Assertions.assertThat(healthRepository.findById(1L)).isEmpty();
    }
  }

  @Nested
  @DisplayName("건강 정보 수정")
  class UpdateHealthInfoTest {

    @Test
    @DisplayName("[성공] 건강 정보 수정 테스트")
    void updateHealthInfo() throws Exception {
      // given
      HealthInfo healthInfo = HealthInfo.builder()
          .healthType("L")
          .level(1)
          .healthDate("2021-10-10")
          .build();
      healthRepository.save(new Health(userRegisterHelper.getCaregiver(), healthInfo));
      // when
      HealthInfo updateHealthInfo = HealthInfo.builder()
          .healthType("H")
          .level(2)
          .healthDate("2021-10-10")
          .build();
      mockMvc.perform(RestDocumentationRequestBuilders.patch("/health/{id}", 1)
              .content(new ObjectMapper().writeValueAsString(updateHealthInfo))
              .contentType(MediaType.APPLICATION_JSON)
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isNoContent())
          .andDo(document("health-update",
              RequestDocumentation.pathParameters(
                  RequestDocumentation.parameterWithName("id").description("수정할 건강 정보 아이디")
              )
          ));
      // then
      Health referenceById = healthRepository.getReferenceById(1L);
      Assertions.assertThat(referenceById.getHealthType()).isEqualTo(HealthType.H);
      Assertions.assertThat(referenceById.getLevel()).isEqualTo(2);
    }

  }

//  @Nested
//  @DisplayName("건강 정보 조회")
//  class GetHealthInfoTest {
//
//    @Test
//    @DisplayName("[성공] 건강 정보 조회 테스트")
//    void getHealthInfo() throws Exception {
//      // given
//      HealthInfo healthInfo = HealthInfo.builder()
//          .healthType("L")
//          .level(1)
//          .healthDate("2021-10-10")
//          .build();
//      healthRepository.save(new Health(userRegisterHelper.getCaregiver(), healthInfo));
//      // when
//      mockMvc.perform(RestDocumentationRequestBuilders.get("/health/{domain-id}", "caregiver")
//              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
//          .andExpect(status().isOk())
//          .andDo(document("health-get",
//              RequestDocumentation.pathParameters(
//                  RequestDocumentation.parameterWithName("domain-id").description("도메인 아이디")
//              )
//          ));
//    }
//  }

}