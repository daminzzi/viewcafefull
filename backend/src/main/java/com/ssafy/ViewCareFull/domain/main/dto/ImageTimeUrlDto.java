package com.ssafy.ViewCareFull.domain.main.dto;

import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import lombok.Getter;

@Getter
public class ImageTimeUrlDto {

  private String url;
  private String time;

  public ImageTimeUrlDto(Meal meal, String serverUrl) {
    this.url = serverUrl + meal.getImage().getImageName();
    this.time = meal.getMealType().getKorean();
  }
}
