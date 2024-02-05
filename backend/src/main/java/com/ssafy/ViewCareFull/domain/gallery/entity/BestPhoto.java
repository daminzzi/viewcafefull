package com.ssafy.ViewCareFull.domain.gallery.entity;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "conference_id")
  private Conference conference;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id")
  private Image image;

  private Integer score;

  public static BestPhoto createBestPhoto(Image image, Conference conference, int score) {
    return BestPhoto.builder()
        .image(image)
        .conference(conference)
        .score(score)
        .build();
  }
}
