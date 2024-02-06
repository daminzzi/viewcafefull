package com.ssafy.ViewCareFull.domain.medicine.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryCreateRequestDto;
import com.ssafy.ViewCareFull.domain.medicine.entity.Medicine;
import com.ssafy.ViewCareFull.domain.medicine.entity.MedicineHistory;
import com.ssafy.ViewCareFull.domain.medicine.repository.MedicineHistoryRepository;
import com.ssafy.ViewCareFull.domain.medicine.repository.MedicineRepository;
import com.ssafy.ViewCareFull.domain.medicine.service.MedicineHistoryService;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
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
@DisplayName("갤러리 컨트롤러 Test")
@ActiveProfiles("test")
class MedicineHistoryControllerTest {


  private MockMvc mockMvc;
  @Autowired
  private DatabaseCleanup databaseCleanup;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private UserRegisterHelper userRegisterHelper;
  @Autowired
  private MedicineHistoryService medicineHistoryService;
  @Autowired
  private MedicineHistoryRepository medicineHistoryRepository;
  @Autowired
  private MedicineRepository medicineRepository;
  @Autowired
  private ObjectMapper objectMapper;


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
  @DisplayName("약 복용 이력 저장 테스트")
  class SaveMedicineHistoryTest {

    @Test
    @DisplayName("[성공] 약 복용 이력 저장 테스트")
    void createMedicineHistory() throws Exception {
      // given
      Medicine medicine = Medicine.builder()
          .medicineName("test")
          .information("test")
          .build();
      medicineRepository.save(medicine);
      MedicineHistoryCreateRequestDto medicineHistoryCreateRequestDto = MedicineHistoryCreateRequestDto.builder()
          .medicineId(1L)
          .medicineType("B")
          .medicineDate("20210901")
          .build();
      Caregiver caregiver = userRegisterHelper.getCaregiver();
      // when
      // with jackson
      mockMvc.perform(RestDocumentationRequestBuilders.post("/medi-his/{domain-id}", "caregiver")
              .content(objectMapper.writeValueAsString(medicineHistoryCreateRequestDto))
              .header("Content-Type", "application/json")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isCreated())
          .andDo(MockMvcRestDocumentation.document("medicine-history-create"));
      // then
      MedicineHistory medicineHistory = medicineHistoryRepository.findAll().get(0);
      Assertions.assertThat(medicineHistory.getMedicine().getId()).isEqualTo(1L);
      Assertions.assertThat(medicineHistory.getUser().getId()).isEqualTo(caregiver.getId());
      Assertions.assertThat(medicineHistory.getMedicineType()).isEqualTo(TodayType.B);
    }

  }

  @Nested
  @DisplayName("약 복용 이력 조회 테스트")
  class GetMedicineHistoryTest {

    @Test
    @DisplayName("[성공] 약 복용 이력 조회 테스트")
    void getMedicineHistory() throws Exception {

      MedicineHistoryCreateRequestDto medicineHistoryCreateRequestDto = MedicineHistoryCreateRequestDto.builder()
          .medicineId(1L)
          .medicineType("B")
          .medicineDate("20210901")
          .build();
      Medicine medicine = Medicine.builder()
          .medicineName("test")
          .information("test")
          .build();
      medicineRepository.save(medicine);
      Caregiver caregiver = userRegisterHelper.getCaregiver();
      MedicineHistory medicineHistory = medicineHistoryCreateRequestDto.toEntity(caregiver, medicine);
      medicineHistoryRepository.save(medicineHistory);

      mockMvc.perform(RestDocumentationRequestBuilders.get("/medi-his/{domain-id}", "caregiver")
              .header("Authorization", userRegisterHelper.getCaregiverAccessToken()))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentation.document("medicine-history-get"));
    }

  }

}