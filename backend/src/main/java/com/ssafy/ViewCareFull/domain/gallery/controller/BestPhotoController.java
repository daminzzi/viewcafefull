package com.ssafy.ViewCareFull.domain.gallery.controller;

import com.ssafy.ViewCareFull.domain.gallery.dto.ConferenceBestPhotoResponse;
import com.ssafy.ViewCareFull.domain.gallery.service.BestPhotoService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/bestphoto")
@RequiredArgsConstructor
public class BestPhotoController {

  private final BestPhotoService bestPhotoService;

  @PostMapping("/{sessionId}")
  public ResponseEntity<String> writeBestPhoto(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestBody(required = false) Map<String, Object> params,
      @PathVariable("sessionId") String sessionId) throws IOException {
    bestPhotoService.writeBestPhoto(securityUsers, params, sessionId);
    return new ResponseEntity<>("create success", HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<ConferenceBestPhotoResponse> getBestPhoto(
      @RequestParam("conferenceId") String coneferenceId) {
    return new ResponseEntity<>(bestPhotoService.getBestPhoto(coneferenceId), HttpStatus.OK);
  }
}
