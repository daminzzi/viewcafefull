package com.ssafy.ViewCareFull.domain.medicine.controller;

import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryCreateRequestDto;
import com.ssafy.ViewCareFull.domain.medicine.dto.MedicineHistoryResponseDto;
import com.ssafy.ViewCareFull.domain.medicine.service.MedicineHistoryService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medi-his")
@RequiredArgsConstructor
public class MedicineHistoryController {

  private final MedicineHistoryService medicineHistoryService;

  @PostMapping("/{domain-id}")
  public ResponseEntity<String> createMedicineHistory(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("domain-id") String domainId,
      @RequestBody MedicineHistoryCreateRequestDto medicineHistoryCreateRequestDto) {
    medicineHistoryService.createMedicineHistory(securityUsers.getUser(), medicineHistoryCreateRequestDto);
    return ResponseEntity.created(null).body("success");
  }

  @GetMapping("/{domain-id}")
  public ResponseEntity<List<MedicineHistoryResponseDto>> getMedicineHistory(
      @PathVariable("domain-id") String domainId) {
    return ResponseEntity.ok(medicineHistoryService.getMedicineHistory(domainId));
  }

}
