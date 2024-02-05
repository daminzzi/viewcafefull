package com.ssafy.ViewCareFull.domain.gallery.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ConferenceBestPhotoResponse {

  private String conferenceId;
  private List<BestPhotoDto> bestPhotos;

  public ConferenceBestPhotoResponse(String conferenceId, List<BestPhotoDto> bestPhotos) {
    this.conferenceId = conferenceId;
    this.bestPhotos = bestPhotos;
  }
}
