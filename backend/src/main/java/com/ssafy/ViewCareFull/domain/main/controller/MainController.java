package com.ssafy.ViewCareFull.domain.main.controller;

import com.ssafy.ViewCareFull.domain.main.dto.MainResponseDto;
import com.ssafy.ViewCareFull.domain.main.service.MainService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/main")
@RestController
public class MainController {

  private final MainService mainService;

  @GetMapping("/{domain-id}")
  public ResponseEntity<MainResponseDto> getMain(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("domain-id") String domainId,
      @RequestParam("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
    return ResponseEntity.ok(mainService.getMain(securityUsers, domainId, date));
  }
}
