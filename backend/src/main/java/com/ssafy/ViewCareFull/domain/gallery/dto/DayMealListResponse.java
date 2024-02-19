package com.ssafy.ViewCareFull.domain.gallery.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class DayMealListResponse {

  private String day;
  private List<MealImageDto> images;

  public DayMealListResponse(LocalDate day, List<MealImageDto> images) {
    this.day = day.toString();
    this.images = images;
  }
}
