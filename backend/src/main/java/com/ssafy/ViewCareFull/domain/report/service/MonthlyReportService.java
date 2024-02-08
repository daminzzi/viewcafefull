package com.ssafy.ViewCareFull.domain.report.service;

import com.ssafy.ViewCareFull.domain.report.dto.HealthIndicatorData;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.util.OpenAIApi;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MonthlyReportService {

  private final OpenAIApi openAIApi;


  public MonthlyReport createMonthlyReport(SecurityUsers securityUser, String id, String month) {
    HealthIndicatorData healthData = HealthIndicatorData.builder()
        .bloodPressure(HealthIndicatorData.BloodPressure.builder()
            .systolic(
                List.of(120, 122, 118, 125, 123, 128, 121, 126, 124, 120, 122, 119, 128, 130, 125, 126, 122, 120,
                    124,
                    127, 130, 123, 121, 128, 126, 124, 122, 129, 127, 130))
            .diastolic(
                List.of(80, 82, 79, 85, 83, 88, 81, 86, 84, 80, 82, 79, 88, 90, 85, 86, 82, 80, 84, 87, 90, 83, 81,
                    88,
                    86, 84, 82, 89, 87, 90))
            .build())
        .bloodSugar(HealthIndicatorData.BloodSugar.builder()
            .fasting(
                List.of(92, 88, 94, 90, 96, 98, 91, 97, 93, 89, 95, 92, 99, 100, 94, 96, 93, 91, 95, 98, 100, 96,
                    92,
                    99, 97, 95, 93, 100, 98, 101))
            .postprandial(
                List.of(112, 108, 114, 110, 116, 118, 111, 117, 113, 109, 115, 112, 119, 120, 114, 116, 113, 111,
                    115,
                    118, 120, 116, 112, 119, 117, 115, 113, 120, 118, 121))
            .build())
        .additionalContext(HealthIndicatorData.AdditionalContext.builder()
            .age(75)
            .gender("Female")
            .build())
        .build();

    return openAIApi.getMontlyReportResponse(healthData);
  }
}
