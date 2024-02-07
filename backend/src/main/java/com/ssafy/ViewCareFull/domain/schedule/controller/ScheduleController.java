package com.ssafy.ViewCareFull.domain.schedule.controller;

import com.ssafy.ViewCareFull.domain.schedule.dto.ScheduleListDto;
import com.ssafy.ViewCareFull.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ScheduleListDto> readHospitalSchedule(@PathVariable("domain-id") String domainId) {
    return ResponseEntity.ok().body(scheduleService.getScheduleList(domainId));
  }
}
