package com.ssafy.ViewCareFull.domain.report.controller;

import com.ssafy.ViewCareFull.domain.ffmpeg.service.FFmpegService;
import com.ssafy.ViewCareFull.domain.report.dto.MonthlyReport;
import com.ssafy.ViewCareFull.domain.report.dto.RequestReportDto;
import com.ssafy.ViewCareFull.domain.report.service.MonthlyMovieService;
import com.ssafy.ViewCareFull.domain.report.service.MonthlyReportService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Slf4j
public class MonthlyReportController {

  private final MonthlyReportService monthlyReportService;
  private final MonthlyMovieService monthlyMovieService;
  private final FFmpegService ffmpegService;

  @GetMapping("/{id}")
  public ResponseEntity<MonthlyReport> getMonthlyReport(@PathVariable("id") long id, @RequestParam int month) {
    return ResponseEntity.ok().body(monthlyReportService.getMonthlyReport(id, month));
  }

  @PostMapping
  public ResponseEntity<Void> createMonthlyReport(@AuthenticationPrincipal SecurityUsers securityUser,
      @RequestBody RequestReportDto requestReportDto) {
    monthlyReportService.createMonthlyReport(securityUser, requestReportDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/movie_test")
  public ResponseEntity<String> test(@AuthenticationPrincipal SecurityUsers securityUser,
      @RequestParam int month,
      @RequestParam int year) {
    monthlyMovieService.createMonthlyMovie(securityUser, year, month);
    log.info("video create success");
    return ResponseEntity.ok().body("success");
  }


}
