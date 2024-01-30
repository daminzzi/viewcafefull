package com.ssafy.ViewCareFull.domain.gallery.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class GalleryResponseDto {

  private final int pageNumber;
  private List<ImageDateResponse> data;
  @JsonIgnore
  private Map<String, ImageDateResponse> map = new HashMap<>();
  @JsonIgnore
  private String fileServerUrl;

  public GalleryResponseDto(Page<Image> page, String fileServerUrl) {
    this.fileServerUrl = fileServerUrl;
    this.pageNumber = page.getNumber() + 1;
    page.getContent().forEach(this::inputToMap);
    ArrayList<ImageDateResponse> tmpArray = map.values().stream()
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    tmpArray.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    this.data = tmpArray;
  }

  private void inputToMap(Image image) {
    String date = image.getImageDateTime().toLocalDate().toString();
    ImageDateResponse imageDateResponse = map.getOrDefault(date, new ImageDateResponse(date));
    imageDateResponse.addImage(image, fileServerUrl);
    map.put(date, imageDateResponse);
  }
}
