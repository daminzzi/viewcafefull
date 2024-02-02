package com.ssafy.ViewCareFull.domain.health.controller;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.health.service.HealthService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHealthInfo(@AuthenticationPrincipal SecurityUsers securityUser,
      @PathVariable("id") String id) {
    healthService.deleteHealthInfo(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateHealthInfo(@AuthenticationPrincipal SecurityUsers securityUser,
      @PathVariable("id") String id,
      @RequestBody HealthInfo healthInfo) {
    healthService.updateHealthInfo(id, healthInfo);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
