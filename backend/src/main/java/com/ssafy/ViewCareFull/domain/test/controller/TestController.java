package com.ssafy.ViewCareFull.domain.test.controller;

import com.ssafy.ViewCareFull.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;

  @GetMapping("/swagger/test")
  public ResponseEntity<String> test(@RequestParam(required = false) String text) {
    String serviceText = testService.getTest(text);
    if (serviceText.equals("null")) {
      return ResponseEntity.badRequest().body("null");
    }
    if (serviceText.equals("email")) {
      return ResponseEntity.ok("email");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notEmail");
  }


}
