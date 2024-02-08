package com.ssafy.ViewCareFull.domain.condition.controller;

import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionResponseDto;
import com.ssafy.ViewCareFull.domain.condition.service.ConditionService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/condition")
@Slf4j
public class ConditionController {

  private final ConditionService conditionService;

  @PostMapping
  public ResponseEntity<String> saveOrUpdate(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestBody ConditionRequestDto requestDto) {
    log.info(requestDto.getCondition());
    return ResponseEntity.status(conditionService.saveOrUpdate(securityUsers, requestDto)).body("success");
  }

  @GetMapping
  public ResponseEntity<List<ConditionResponseDto>> getCondition(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestParam("start") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate start,
      @RequestParam("end") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate end) {
    return ResponseEntity.ok(conditionService.getCondition(securityUsers, start, end));
  }


}
