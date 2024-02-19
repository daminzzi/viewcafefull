package com.ssafy.ViewCareFull.domain.message.controller;

import com.ssafy.ViewCareFull.domain.message.dto.MessageDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageListResponseDto;
import com.ssafy.ViewCareFull.domain.message.dto.MessageRequestDto;
import com.ssafy.ViewCareFull.domain.message.service.MessageService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageController {

  private final MessageService messageService;

  @GetMapping
  public ResponseEntity<MessageListResponseDto> getMessages(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      @RequestParam(value = "size", defaultValue = "6") int size) {
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by("sendDateTime").descending());
    return ResponseEntity.ok(messageService.getMessages(securityUsers, pageable, keyword));
  }

  @PostMapping
  public ResponseEntity<String> sendMessage(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestBody MessageRequestDto message) {
    messageService.sendMessage(securityUsers, message);
    return ResponseEntity.status(201).body("success");
  }

  @GetMapping("/{id}")
  public ResponseEntity<MessageDto> getMessage(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("id") String id) {
    return ResponseEntity.ok(messageService.readMessage(securityUsers, id));
  }

  @PostMapping("/{id}")
  public ResponseEntity<String> readMessage(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("id") String id) {
    messageService.readMessage(securityUsers, id);
    return ResponseEntity.ok("success");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMessage(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @PathVariable("id") String id) {
    messageService.deleteMessage(securityUsers, id);
    return ResponseEntity.status(204).body("success");
  }
}
