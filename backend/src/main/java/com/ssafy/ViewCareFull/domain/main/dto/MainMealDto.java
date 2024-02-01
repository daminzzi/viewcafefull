package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MainMealDto {

  private List<ImageTimeUrlDto> images = new ArrayList<>();

  public MainMealDto(List<Meal> mealInfo, String serverUrl) {
    for (Meal meal : mealInfo) {
      this.images.add(new ImageTimeUrlDto(meal, serverUrl));
    }
  }
}
