package com.ssafy.ViewCareFull.domain.conference.controller;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conference")
public class ConferenceController {

  private final ConferenceService conferenceService;

  @PostMapping
  public ResponseEntity<Void> reserveConference(@AuthenticationPrincipal SecurityUsers securityUser,
      @RequestBody ConferenceReservationDto conferenceReservation) {
    conferenceService.reserveConference(conferenceReservation);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateConferencePermissionState(
      @PathVariable Long id, @RequestBody ConferenceStateDto conferenceStateDto) {
    conferenceService.updateConferencePermissionState(id, conferenceStateDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
