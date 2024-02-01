package com.ssafy.ViewCareFull.domain.gallery.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ViewCareFull.domain.gallery.dto.DayMealListResponse;
import com.ssafy.ViewCareFull.domain.gallery.service.MealService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

  private final MealService mealService;

  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<String> writeMeal(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestPart("day") String day,
      @RequestPart("time") String time,
      @RequestPart("image") MultipartFile image) {
    LocalDate localDate = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyyMMdd"));
    mealService.createMeal(securityUsers, localDate, time, image);
    return ResponseEntity.ok("success");
  }

  @GetMapping
  public ResponseEntity<DayMealListResponse> getMeal(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestParam("day") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate day) {
    return ResponseEntity.ok(mealService.getDayMealList(securityUsers, day));
  }


}
