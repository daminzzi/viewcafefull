package com.ssafy.ViewCareFull.domain.galary.entity;

import com.ssafy.ViewCareFull.domain.user.entity.user.Caregiver;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Caregiver caregiver;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "image_datetime")
  private LocalDateTime imageDateTime;

}
