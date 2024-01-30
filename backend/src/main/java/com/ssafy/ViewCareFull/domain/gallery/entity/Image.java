package com.ssafy.ViewCareFull.domain.gallery.entity;

import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "image_name")
  private String imageName;

  @CreatedDate
  @Column(name = "image_datetime")
  private LocalDateTime imageDateTime;

  public Image(String savePath, String fileName, CaregiverIdDto caregiverIdDto) {
    this.userId = caregiverIdDto.getCaregiverId();
    this.imageUrl = savePath;
    this.imageName = fileName;
  }
}
