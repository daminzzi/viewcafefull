package com.ssafy.ViewCareFull.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTime {

  @CreatedDate
  @Column(name = "created_datetime", updatable = false)
  private LocalDateTime createdDateTime;

  @LastModifiedDate
  @Column(name = "last_modified_datetime")
  private LocalDateTime lastModifiedDateTime;

}

