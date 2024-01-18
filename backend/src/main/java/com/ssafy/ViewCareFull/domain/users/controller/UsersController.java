package com.ssafy.ViewCareFull.domain.users.controller;

import com.ssafy.ViewCareFull.domain.users.dto.JoinForm;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

  private final UsersService usersService;

  @PostMapping
  public ResponseEntity<Void> singup(@RequestBody JoinForm joinForm) {
    usersService.singup(joinForm);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/validation/{id}")
  public ResponseEntity<Void> validation(@PathVariable(name = "id") String id) {
    if (usersService.duplicatedId(id)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
