package com.ssafy.ViewCareFull.domain.test.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

  public String getTest(String text) {
    // email validation
    if (text == null || text.isEmpty()) {
      return "null";
    }
    if (text.contains("@")) {
      return "email";
    }
    return "notEmail";
  }

}
