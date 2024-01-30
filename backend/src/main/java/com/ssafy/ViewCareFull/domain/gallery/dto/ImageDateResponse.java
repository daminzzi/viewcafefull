package com.ssafy.ViewCareFull.domain.gallery.dto;

import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ImageDateResponse {

  private String date;
  private List<String> images;
  private List<String> thumnail;

  public ImageDateResponse(String date) {
    this.date = date;
    this.images = new ArrayList<>();
    this.thumnail = new ArrayList<>();
  }

  public void addImage(Image image, String fileServerUrl) {
    this.images.add(fileServerUrl + image.getImageName());
    this.thumnail.add(fileServerUrl + image.getImageName());
  }
}
