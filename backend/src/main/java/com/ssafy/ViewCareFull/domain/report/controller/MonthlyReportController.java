package com.ssafy.ViewCareFull.domain.report.controller;

import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.service.MonthlyReportService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class MonthlyReportController {

  private final MonthlyReportService monthlyReportService;

  @GetMapping
  public ResponseEntity<MonthlyReport> createMonthlyReport(@AuthenticationPrincipal SecurityUsers securityUser,
      @RequestParam String month) {
    return ResponseEntity.ok().body(monthlyReportService.createMonthlyReport(securityUser, month));
  }

}
