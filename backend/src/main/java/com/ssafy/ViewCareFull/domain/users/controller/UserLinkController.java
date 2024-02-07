package com.ssafy.ViewCareFull.domain.users.controller;

import com.ssafy.ViewCareFull.domain.users.dto.LinkRequestDto;
import com.ssafy.ViewCareFull.domain.users.dto.LinkUpdateRequestDto;
import com.ssafy.ViewCareFull.domain.users.dto.UserLinkListResponseDto;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserTypeException;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/{domain_id}")
  public ResponseEntity<String> addLink(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("domain_id") String domainId,
      @RequestBody LinkRequestDto linkRequestDto) {
    userLinkService.addLink(securityUsers, linkRequestDto);
    return ResponseEntity.created(null).body("success");
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> updateLink(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("id") Long id,
      @RequestBody LinkUpdateRequestDto linkUpdateRequestDto) {
    if (!securityUsers.getUser().getUserType().equals("Hospital")) {
      throw new UserTypeException();
    }
    userLinkService.updateLink(id, linkUpdateRequestDto);
    return ResponseEntity.ok("success");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteLink(
      @PathVariable("id") Long id) {
    userLinkService.deleteLink(id);
    return ResponseEntity.ok("success");
  }
}
