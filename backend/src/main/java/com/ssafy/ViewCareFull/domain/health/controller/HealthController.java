package com.ssafy.ViewCareFull.domain.health.controller;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.health.service.HealthService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

  private final HealthService healthService;

  @PostMapping("/{domain-id}")
  public ResponseEntity<Void> createHealthInfo(@AuthenticationPrincipal SecurityUsers securityUser,
      @PathVariable("domain-id") String domainId,
      @RequestBody HealthInfo healthInfo) {
    healthService.saveHealthInfo(domainId, healthInfo);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


}
