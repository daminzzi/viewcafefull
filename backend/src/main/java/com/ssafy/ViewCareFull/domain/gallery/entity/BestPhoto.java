package com.ssafy.ViewCareFull.domain.gallery.entity;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import jakarta.persistence.*;
import lombok.Getter;



@Entity
@Getter
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
}
