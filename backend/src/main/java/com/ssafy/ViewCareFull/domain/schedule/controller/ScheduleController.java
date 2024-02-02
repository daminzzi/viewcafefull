package com.ssafy.ViewCareFull.domain.schedule.controller;

import com.ssafy.ViewCareFull.domain.schedule.dto.ScheduleListDto;
import com.ssafy.ViewCareFull.domain.schedule.service.ScheduleService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/{domain-id}")
  public ResponseEntity<ScheduleListDto> readHospitalSchedule(@AuthenticationPrincipal SecurityUsers securityUser,
      @PathVariable("domain-id") String domainId) {
    return ResponseEntity.ok().body(scheduleService.getScheduleList(securityUser, domainId));
  }
}
