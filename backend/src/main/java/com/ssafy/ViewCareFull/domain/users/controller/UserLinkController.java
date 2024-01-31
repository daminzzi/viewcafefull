package com.ssafy.ViewCareFull.domain.users.controller;

import com.ssafy.ViewCareFull.domain.users.dto.UserLinkListResponseDto;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-link")
public class UserLinkController {

  private final UserLinkService userLinkService;

  @GetMapping("/{type}")
  public ResponseEntity<UserLinkListResponseDto> getLinkList(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("type") String type) {
    return ResponseEntity.ok(userLinkService.getLinkList(securityUsers, type));
  }

}
