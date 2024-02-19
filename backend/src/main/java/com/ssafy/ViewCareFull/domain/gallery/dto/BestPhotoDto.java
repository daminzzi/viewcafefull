package com.ssafy.ViewCareFull.domain.gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestPhotoDto {

  private String imageId;
  private int score;
  private String imageUrl;
}
