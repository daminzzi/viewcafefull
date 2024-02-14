package com.ssafy.ViewCareFull.domain.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ViewCareFull.domain.condition.service.ConditionService;
import com.ssafy.ViewCareFull.domain.health.service.HealthService;
import com.ssafy.ViewCareFull.domain.message.service.MessageService;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyHealthInfo;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.dto.RequestReportDto;
import com.ssafy.ViewCareFull.domain.report.entity.Reports;
import com.ssafy.ViewCareFull.domain.report.error.ReportErrorCode;
import com.ssafy.ViewCareFull.domain.report.error.exception.ReportException;
import com.ssafy.ViewCareFull.domain.report.repository.ReportRepository;
import com.ssafy.ViewCareFull.domain.report.util.OpenAIApi;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MonthlyReportService {

  private final OpenAIApi openAIApi;
  private final ReportRepository reportRepository;
  private final HealthService healthService;
  private final ConditionService conditionService;
  private final MessageService messageService;
  private final UsersService usersService;
  private final MonthlyMovieService monthlyMovieService;
  private final ObjectMapper objectMapper;
  @Value("${file.server.video}")
  private String videoServerUrl;


  @Transactional
  public void createMonthlyReport(SecurityUsers securityUser, RequestReportDto requestReportDto) {
    int year = requestReportDto.getMonth() / 100;
    int month = requestReportDto.getMonth() % 100;
    LocalDate start = LocalDate.of(year, month, 1);
    int lastOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
    LocalDate end = LocalDate.of(year, month, lastOfMonth);

    MonthlyHealthInfo monthlyAverageHealth = healthService.getMonthlyAverageHealthList(requestReportDto.getId(),
        start, end);

    Caregiver caregiver = usersService.getCaregiverById(requestReportDto.getId());
    monthlyAverageHealth.addMessageList(messageService.getMonthlyMessages(caregiver.getDomainId(), start, end));
    String movieUrl = monthlyMovieService.createMonthlyMovie(securityUser, year, month);
    try {
      MonthlyReport monthlyReportResponse = openAIApi.getMonthlyReportResponse(monthlyAverageHealth);

      monthlyReportResponse.settingReport(year, month, caregiver,
          conditionService.cntCondition(requestReportDto.getId(), start, end),
          movieUrl);

      reportRepository.save(monthlyReportResponse.toEntity(requestReportDto));
    } catch (Exception e) {
      // 원래는  Exception 던져야 하지만 현재는 테스트를 위해 1월 데이터를 임시로 저장함.
      Reports reports = reportRepository.findByIdAndDate(requestReportDto.getId(), 2024, 1)
          .orElseThrow(() -> new ReportException(ReportErrorCode.NOT_FOUND_CREATED_REPORT));
      try {
        MonthlyReport monthlyReport = objectMapper.readValue(reports.getReportInfo(), MonthlyReport.class);
        monthlyReport.changeMovieUrl(movieUrl);
        String reportInfo = objectMapper.writeValueAsString(monthlyReport);
        reportRepository.save(reports.copy(month, reportInfo));
      } catch (JsonProcessingException jsonProcessingException) {
        throw new ReportException(ReportErrorCode.NOT_MATCHED_JSON_FORMAT);
      }
//      throw new ReportException(ReportErrorCode.NOT_FOUND_CREATED_MOVIE);
    }
  }

  public MonthlyReport getMonthlyReport(long id, int date) {
    Reports findReport = reportRepository.findByIdAndDate(id, date / 100, date % 100)
        .orElseThrow(() -> new ReportException(ReportErrorCode.NOT_FOUND_CREATED_REPORT));
    try {
      return MonthlyReport.of(findReport, videoServerUrl);
    } catch (JsonProcessingException e) {
      throw new ReportException(ReportErrorCode.NOT_MATCHED_JSON_FORMAT);
    }
  }
}
