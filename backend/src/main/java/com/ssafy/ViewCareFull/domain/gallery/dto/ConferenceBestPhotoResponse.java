package com.ssafy.ViewCareFull.domain.gallery.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ConferenceBestPhotoResponse {

  private String conferenceId;
  private String userId;
  private List<BestPhotoDto> bestPhotoList;

  public ConferenceBestPhotoResponse(String conferenceId, String userId, List<BestPhotoDto> bestPhotos) {
    this.conferenceId = conferenceId;
    this.userId = userId;
    this.bestPhotoList = bestPhotos;
  }
}
