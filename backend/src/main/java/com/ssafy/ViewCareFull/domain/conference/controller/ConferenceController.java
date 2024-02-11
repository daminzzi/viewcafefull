package com.ssafy.ViewCareFull.domain.conference.controller;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfoSummaryDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceTodayListDto;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference")
public class ConferenceController {

  private final ConferenceService conferenceService;

  @PostMapping
  public ResponseEntity<Void> reserveConference(@AuthenticationPrincipal SecurityUsers securityUser,
      @RequestBody ConferenceReservationDto conferenceReservation) {
    conferenceService.reserveConference(securityUser, conferenceReservation);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateConferencePermissionState(@AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable Long id,
      @RequestBody ConferenceStateDto conferenceStateDto) {
    conferenceService.updateConferencePermissionState(securityUsers, id, conferenceStateDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
    conferenceService.deleteConference(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/{type}/list")
  public ResponseEntity<ConferenceTodayListDto> getConferenceList(
      @AuthenticationPrincipal SecurityUsers securityUser, @PathVariable String type,
      @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
      @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
      @RequestParam(value = "order", required = false) String order,
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(conferenceService.getConferenceList(securityUser, type, startDate, endDate, page, order));
  }

  @GetMapping("/per")
  public ResponseEntity<ConferenceInfoSummaryDto> getConferenceList(
      @AuthenticationPrincipal SecurityUsers securityUser) {
    return ResponseEntity.status(HttpStatus.OK).body(conferenceService.getMainConferenceList(securityUser));
  }
}
