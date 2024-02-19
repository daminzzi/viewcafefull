package com.ssafy.ViewCareFull.domain.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealImageDto {

  private String time;
  private String url;
}
